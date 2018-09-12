package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import domain.Actor;
import domain.Handyworker;
import domain.MaintenanceRequest;
import exceptions.ResourceNotUniqueException;
import repositories.HandyworkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.CheckUtils;

@Service
@Transactional
public class HandyworkerService {

    @Autowired
    private HandyworkerRepository repository;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private ActorService actorService;


    public HandyworkerService()
    {
        super();
    }

    public Handyworker findPrincipal()
    {
        if (!LoginService.isAuthenticated()) return null;

        UserAccount userAccount = LoginService.getPrincipal();
        if (userAccount == null) return null;

        return repository.findByUserAccount(userAccount);
    }

    public Handyworker create()
    {
        final Handyworker h = new Handyworker();
        return h;
    }

    public Collection<Handyworker> findAll()
    {
        final Collection<Handyworker> res = this.repository.findAll();
        return res;
    }

    public Handyworker getPrincipal()
    {
        CheckUtils.checkPrincipalAuthority(Authority.HANDYWORKER);
        Handyworker principal = findPrincipal();
        Assert.notNull(principal);
        return principal;
    }

    public Handyworker save(final Handyworker handyworker)
    {
        Assert.notNull(handyworker);
        Assert.notNull(handyworker.getUserAccount());
        final Handyworker res = this.repository.save(handyworker);

        return res;
    }

    public Handyworker createAsNewHandyworker(final Handyworker handyworker) throws ResourceNotUniqueException
    {
        CheckUtils.checkUnauthenticated();
        CheckUtils.checkNotExists(handyworker);

        handyworker.setUserAccount(this.userAccountService.create(handyworker.getUserAccount().getUsername(), handyworker.getUserAccount().getPassword(), Authority.HANDYWORKER));

        return this.repository.save(handyworker);
    }

    public List<MaintenanceRequest> findServedMainteinanceRequest(final Handyworker worker)
    {
        Assert.notNull(worker);
        return this.repository.findServedMaintenanceRequest(worker);

    }

    public List<MaintenanceRequest> findNotServedMainteinanceRequest(final Handyworker worker)
    {
        Assert.notNull(worker);
        return this.repository.findNotServedMaintenanceRequest(worker);

    }

}
