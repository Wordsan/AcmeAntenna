package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;

import javax.transaction.Transactional;

import domain.Actor;
import domain.Agent;
import exceptions.ResourceNotUniqueException;
import repositories.AgentRepository;
import security.Authority;
import security.UserAccountService;
import utilities.CheckUtils;

@Service
@Transactional
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private ActorService actorService;


    public AgentService()
    {
        super();
    }

    public Agent findPrincipal()
    {
        final Actor principal = this.actorService.findPrincipal();
        if (principal instanceof Agent) {
            return (Agent) principal;
        }
        return null;
    }

    public Agent getPrincipal()
    {
        CheckUtils.checkPrincipalAuthority(Authority.AGENT);
        Agent principal = findPrincipal();
        Assert.notNull(principal);
        return principal;
    }

    public Agent createAsNewAgent(final Agent agent) throws ResourceNotUniqueException
    {
        CheckUtils.checkUnauthenticated();
        CheckUtils.checkNotExists(agent);

        agent.setUserAccount(this.userAccountService.create(agent.getUserAccount().getUsername(), agent.getUserAccount().getPassword(), Authority.AGENT));

        return this.agentRepository.save(agent);
    }

    public Agent create()
    {
        final Agent a = new Agent();
        a.setBanned(false);

        return a;
    }

    public Agent save(final Agent agent)
    {
        Assert.notNull(agent);
        Assert.notNull(agent.getUserAccount());
        return this.agentRepository.save(agent);
    }

    public Collection<Agent> findAll()
    {

        return this.agentRepository.findAll();
    }

}
