
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;
import domain.User;
import exceptions.UsernameNotUniqueException;

/**
 * Tests the following use cases from the requirements document:
 * - Register to the system as a user.
 */
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest extends AbstractTest {

	@Autowired
	private UserService					userService;
	@Autowired
	private UserAccountService			userAccountService;
	@Autowired
	private MaintenanceRequestService	maintenanceRequestService;
	@Autowired
	private HandyworkerService			handyworkerService;
	@Autowired
	private AntennaService				antennaService;


	// Test successful creation of an user.
	@Test
	public void testCreateUser() throws UsernameNotUniqueException {
		this.unauthenticate();

		// Make a user.
		User user = new User();
		user.setName("The Senate");
		user.setSurname("Palpatine");
		user.setEmail("palpatine@galacticrepublic.com");
		user.setUserAccount(new UserAccount("senate", "thepasswordistreason", Authority.USER));

		// Create this user.
		this.userService.createAsNewUser(user);

		// Flush changes.
		this.flushTransaction();

		// Ensure it exists.
		user = (User) this.getActor("senate");
		Assert.notNull(user);

		// Ensure it can log in.
		Assert.isTrue(this.userAccountService.passwordMatchesAccount(user.getUserAccount(), "thepasswordistreason"));
	}

	// Test creation of an user fails if already logged in.
	@Test(expected = AccessDeniedException.class)
	public void testCreateUserFailsIfLoggedIn() throws UsernameNotUniqueException {
		this.authenticate("user1");

		// Make a user.
		final User user = new User();
		user.setName("The Senate");
		user.setSurname("Palpatine");
		user.setEmail("palpatine@galacticrepublic.com");
		user.setUserAccount(new UserAccount("senate", "thepasswordistreason", Authority.USER));

		// Create this user.
		this.userService.createAsNewUser(user);

		// Shouldn't reach this point.
	}

	// Test creation of an user fails if username is not unique.
	@Test(expected = UsernameNotUniqueException.class)
	public void testCreateUserFailsIfUsernameNotUnique() throws UsernameNotUniqueException {
		this.unauthenticate();

		// Make a user.
		final User user = new User();
		user.setName("The Senate");
		user.setSurname("Palpatine");
		user.setEmail("palpatine@galacticrepublic.com");

		// user1 is already taken.
		user.setUserAccount(new UserAccount("user1", "thepasswordistreason", Authority.USER));

		// Create this user.
		this.userService.createAsNewUser(user);

		// Shouldn't reach this point.
	}

}
