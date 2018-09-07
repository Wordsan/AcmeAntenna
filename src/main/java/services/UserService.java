package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import javax.transaction.Transactional;

import domain.Actor;
import domain.MaintenanceRequest;
import domain.User;
import exceptions.ResourceNotUniqueException;
import repositories.UserRepository;
import security.Authority;
import security.UserAccountService;
import utilities.CheckUtils;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ActorService actorService;


    public User findPrincipal()
    {
        final Actor principal = this.actorService.findPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }

    public User getPrincipal()
    {
        final Actor principal = this.actorService.findPrincipal();
        Assert.isTrue(principal instanceof User);
        return (User) principal;
    }

    public User createAsNewUser(final User user) throws ResourceNotUniqueException
    {
        CheckUtils.checkUnauthenticated();
        CheckUtils.checkNotExists(user);

        user.setUserAccount(this.userAccountService.create(user.getUserAccount().getUsername(), user.getUserAccount().getPassword(), Authority.USER));

        return this.repository.save(user);
    }

    public User save(final User user)
    {
        Assert.notNull(user);
        final User res = this.repository.save(user);
        Assert.notNull(res);
        return res;
    }

    public List<MaintenanceRequest> findServedMainteinanceRequest(final User user)
    {
        Assert.notNull(user);
        return this.repository.findServedMaintenanceRequest(user);

    }

    public List<MaintenanceRequest> findNotServedMainteinanceRequest(final User user)
    {
        Assert.notNull(user);
        return this.repository.findNotServedMaintenanceRequest(user);

    }

}
