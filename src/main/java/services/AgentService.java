
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.AgentRepository;
import security.Authority;
import security.UserAccountService;
import utilities.CheckUtils;
import domain.Actor;
import domain.Agent;
import exceptions.UsernameNotUniqueException;

@Service
@Transactional
public class AgentService {

	@Autowired
	private AgentRepository		agentRepository;
	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private ActorService		actorService;


	public AgentService() {
		super();
	}

	public Agent findPrincipal() {
		final Actor principal = this.actorService.findPrincipal();
		if (principal instanceof Agent)
			return (Agent) principal;
		return null;
	}

	public Agent createAsNewAgent(final Agent agent) throws UsernameNotUniqueException {
		CheckUtils.checkUnauthenticated();
		CheckUtils.checkNotExists(agent);

		agent.setUserAccount(this.userAccountService.create(agent.getUserAccount().getUsername(), agent.getUserAccount().getPassword(), Authority.AGENT));

		return this.agentRepository.save(agent);
	}

}
