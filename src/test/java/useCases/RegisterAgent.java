
package useCases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;

import exceptions.ResourceNotUniqueException;
import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import services.AgentService;
import services.AntennaService;
import services.HandyworkerService;
import utilities.AbstractTest;
import domain.Agent;

/**
 * Tests the following use cases from the requirements document:
 * - Register to the system as a user.
 */
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterAgent extends AbstractTest {

	@Autowired
	private AgentService		agentService;

	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private HandyworkerService	handyworkerService;
	@Autowired
	private AntennaService		antennaService;


	//Register to the system as an agent.

	@Test
	public void RegisterAsAnAgent() throws Exception
	{
		this.unauthenticate();
		final Agent agent = this.agentService.create();
		agent.setEmail("email@email.com");
		agent.setName("Agent");
		agent.setSurname("Agent");
		final UserAccount uaSaved = userAccountService.create("Agent23", "Agent", Authority.AGENT);
		agent.setUserAccount(uaSaved);
		final Agent res = this.agentService.save(agent);
		Assert.isTrue(this.agentService.findAll().contains(res));

	}
	//Register to the system as an agent.
	//Registering without providing an userAccount
	@Test(expected = IllegalArgumentException.class)
	public void RegisterAsAnAgentWithoutUserAccount() throws IllegalArgumentException {
		this.unauthenticate();
		final Agent agent = this.agentService.create();
		agent.setEmail("email@email.com");
		agent.setName("Agent");
		agent.setSurname("Agent");
		final UserAccount ua = null;
		agent.setUserAccount(ua);
		final Agent res = this.agentService.save(agent);

	}
	//Register to the system as an agent.
	//Registering a null agent
	@Test(expected = IllegalArgumentException.class)
	public void RegisterAsANullAgent() throws IllegalArgumentException {
		this.unauthenticate();

		final Agent res = this.agentService.save(null);

	}

}
