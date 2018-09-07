package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import domain.Actor;
import domain.User;
import exceptions.ResourceNotUniqueException;
import repositories.UserRepository;
import security.Authority;
import security.UserAccountService;
import utilities.CheckUtils;

@Service
@Transactional
public class UserService {
    @Autowired private UserRepository repository;
    @Autowired private UserAccountService userAccountService;

    @Autowired private ActorService actorService;

    public User findPrincipal()
    {
        Actor principal = actorService.findPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }

    public User getPrincipal()
    {
        Actor principal = actorService.findPrincipal();
        Assert.isTrue(principal instanceof User);
        return (User) principal;
    }

    public User createAsNewUser(User user) throws ResourceNotUniqueException
    {
        CheckUtils.checkUnauthenticated();
        CheckUtils.checkNotExists(user);

        user.setUserAccount(userAccountService.create(user.getUserAccount().getUsername(), user.getUserAccount().getPassword(), Authority.USER));

        return repository.save(user);
    }

}