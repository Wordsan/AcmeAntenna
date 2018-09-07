/*
 * AbstractTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.Actor;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import utilities.internal.EclipseConsole;

/*
Prepend all tests with this:

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional

 */
public abstract class AbstractTest {

	// Supporting services --------------------------------

	@Autowired
	private LoginService loginService;
	@Autowired
	private JpaTransactionManager				transactionManager;
	@Autowired
	private ActorService actorService;

	// Internal state -------------------------------------

	private final DefaultTransactionDefinition	transactionDefinition;
	private TransactionStatus					currentTransaction;
	private final Properties					entityMap;


	@PersistenceContext
	protected EntityManager entityManager;

	// Constructor ----------------------------------------

	public AbstractTest() {
		EclipseConsole.fix();
		LogManager.getLogger("org.hibernate").setLevel(Level.OFF);
		LogManager.getLogger("cz.jirutka.validator").setLevel(Level.OFF);

		this.transactionDefinition = new DefaultTransactionDefinition();
		this.transactionDefinition.setName("TestTransaction");
		this.transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		try (InputStream stream = new FileInputStream(DatabaseConfig.entityMapFilename)) {
			this.entityMap = new Properties();
			this.entityMap.load(stream);
		} catch (final Throwable oops) {
			throw new RuntimeException(oops);
		}
	}
	// Set up and tear down -------------------------------

	@Before
	public void outerSetUp() {
		startTransaction();
		setUp();
	}
	public void setUp() {}

	@After
	public void outerTearDown() {
		tearDown();
		try {
			// Clear the entity manager if an exception has already been thrown to prevent another exception from happening here.
			if (currentTransaction.isRollbackOnly()) {
				entityManager.clear();
			}
			flushTransaction();
		} finally {
			try {
				unauthenticate();
			} finally {
				rollbackTransaction();
			}
		}
	}
	public void tearDown() {}

	private static boolean hasPopulatedDatabase = false;
	@BeforeClass
	public static void setUpGlobal()
	{
		if (!hasPopulatedDatabase) {
			hasPopulatedDatabase = true;

			String skip = System.getenv("DNT_SKIP_POPULATE_DATABASE");
			if (skip == null || !skip.equals("1")) {
				PopulateDatabase.main(null);
			}
		}

	}

	// Supporting methods ---------------------------------

	protected void authenticate(final String username) {
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;

		if (username == null)
			authenticationToken = null;
		else {
			userDetails = this.loginService.loadUserByUsername(username);
			authenticationToken = new TestingAuthenticationToken(userDetails, null);
			authenticationToken.setAuthenticated(true);
		}

		context = SecurityContextHolder.getContext();
		context.setAuthentication(authenticationToken);

		if (username != null) {
			Assert.assertEquals(username, ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		} else {
			Assert.assertNull(SecurityContextHolder.getContext().getAuthentication());
		}
	}

	protected void unauthenticate() {
		this.authenticate(null);
	}

	protected void checkExceptions(final Class<?> expected, final Class<?> caught) {
		if (expected != null && caught == null)
			throw new RuntimeException(expected.getName() + " was expected");
		else if (expected == null && caught != null)
			throw new RuntimeException(caught.getName() + " was unexpected");
		else if (expected != null && caught != null && !expected.equals(caught))
			throw new RuntimeException(expected.getName() + " was expected, but " + caught.getName() + " was thrown");
	}

	protected void startTransaction() {
		this.currentTransaction = this.transactionManager.getTransaction(this.transactionDefinition);
	}

	protected void commitTransaction() {
		this.transactionManager.commit(this.currentTransaction);
	}

	protected void rollbackTransaction() {
		this.transactionManager.rollback(this.currentTransaction);
	}

	protected void flushTransaction() {
		try {
			this.currentTransaction.flush();
		} finally {
			entityManager.clear();
		}
	}

	protected boolean existsEntity(final String beanName) {
		assert beanName != null && beanName.matches("^[A-Za-z0-9\\-]+$");

		boolean result;

		result = this.entityMap.containsKey(beanName);

		return result;
	}

	protected int getEntityId(final String beanName) {
		assert beanName != null && beanName.matches("^[A-Za-z0-9\\-]+$");
		assert this.existsEntity(beanName);

		int result;
		String id;

		id = (String) this.entityMap.get(beanName);
		result = Integer.valueOf(id);

		return result;
	}

	protected Actor getPrincipal()
	{
		return actorService.getPrincipal();
	}

	protected Actor getActor(String username)
	{
		return actorService.getByUsername(username);
	}
}
