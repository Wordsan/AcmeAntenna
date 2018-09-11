package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import domain.Actor;
import exceptions.OldPasswordDoesntMatchException;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.CheckUtils;
import utilities.ValidationUtils;

@Service
@Transactional
public class ActorService {
    @Autowired private ActorRepository repository;
    @Autowired private UserAccountService userAccountService;
    @Autowired private UserService userService;
    @Autowired private AdministratorService administratorService;

    public Actor findPrincipal()
    {
        if (!LoginService.isAuthenticated()) return null;

        UserAccount userAccount = LoginService.getPrincipal();
        if (userAccount == null) return null;

        // This is faster than using ActorRepository because the SQL Hibernate creates to query
        // classes with subclasses is not very optimized.

        // Doing this this way drops the response time from 2000 to 20 at 200 simultaneous users.
        String authority = userAccount.getAuthorities().iterator().next().getAuthority();
        if (authority.equals(Authority.ADMINISTRATOR)) {
            return administratorService.findPrincipal();
        } else if (authority.equals(Authority.USER)) {
            return userService.findPrincipal();
        }

        return repository.findByUserAccount(userAccount);
    }

    public Actor getPrincipal()
    {
        CheckUtils.checkAuthenticated();
        Actor principal = findPrincipal();
        Assert.notNull(principal);
        return principal;
    }

    public Actor findByUsername(String username)
    {
        return repository.findByUsername(username);
    }

    public Actor getByUsername(String username)
    {
        Actor actor = findByUsername(username);
        Assert.notNull(actor);
        return actor;
    }

    public Actor updateOwnProfile(Actor submittedActor)
    {
        CheckUtils.checkAuthenticated();
        Actor currentActor = getPrincipal();
        CheckUtils.checkEquals(currentActor, submittedActor);
        CheckUtils.checkSameVersion(submittedActor, currentActor);

        currentActor.setName(submittedActor.getName());
        currentActor.setSurname(submittedActor.getSurname());
        currentActor.setEmail(submittedActor.getEmail());
        currentActor.setPhoneNumber(submittedActor.getPhoneNumber());
        currentActor.setPostalAddress(submittedActor.getPostalAddress());
        currentActor.setPictureUrl(submittedActor.getPictureUrl());

        return repository.save(currentActor);
    }

    public Actor updateOwnPassword(final String oldPassword, final String newPassword) throws OldPasswordDoesntMatchException
    {
        CheckUtils.checkAuthenticated();
        Actor currentActor = getPrincipal();

        if (!userAccountService.passwordMatchesAccount(currentActor.getUserAccount(), oldPassword)) {
            throw new OldPasswordDoesntMatchException();
        }

        UserAccount userAccount = currentActor.getUserAccount();
        userAccount.setPassword(newPassword);

        // Validate and throw if bad entity.
        ValidationUtils.validateBean(userAccount);

        currentActor.setUserAccount(this.userAccountService.updatePassword(userAccount, newPassword));
        return repository.save(currentActor);
    }
}