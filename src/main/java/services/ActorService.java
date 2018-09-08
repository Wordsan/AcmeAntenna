package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import domain.Actor;
import exceptions.OldPasswordDoesntMatchException;
import exceptions.ResourceNotFoundException;
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
    @Autowired private Validator validator;
    @PersistenceContext private EntityManager entityManager;


    public Actor findPrincipal()
    {
        if (!LoginService.isAuthenticated()) {
            return null;
        }

        final UserAccount userAccount = LoginService.getPrincipal();
        if (userAccount == null) {
            return null;
        }

        return this.repository.findByUserAccount(userAccount);
    }

    public Actor getPrincipal()
    {
        CheckUtils.checkAuthenticated();
        final Actor principal = this.findPrincipal();
        Assert.notNull(principal);
        return principal;
    }

    public Actor findByUsername(final String username)
    {
        return this.repository.findByUsername(username);
    }

    public Actor getByUsername(final String username)
    {
        final Actor actor = this.findByUsername(username);
        Assert.notNull(actor);
        return actor;
    }

    public Actor updateOwnProfile(final Actor submittedActor)
    {
        CheckUtils.checkAuthenticated();
        final Actor currentActor = this.getPrincipal();
        CheckUtils.checkEquals(currentActor, submittedActor);
        CheckUtils.checkSameVersion(submittedActor, currentActor);

        currentActor.setName(submittedActor.getName());
        currentActor.setSurname(submittedActor.getSurname());
        currentActor.setEmail(submittedActor.getEmail());
        currentActor.setPhoneNumber(submittedActor.getPhoneNumber());
        currentActor.setPostalAddress(submittedActor.getPostalAddress());
        currentActor.setPictureUrl(submittedActor.getPictureUrl());

        return this.repository.save(currentActor);
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

    public List<Actor> findAll()
    {
        return repository.findAll();
    }

    public Actor findById(int id)
    {
        return repository.findOne(id);
    }

    public void setBanned(int id, boolean banned)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        Actor actor = getById(id);
        actor.setBanned(banned);

        repository.save(actor);
    }

    private Actor getById(int id)
    {
        Actor result = findById(id);
        if (result == null) throw new ResourceNotFoundException();
        return result;
    }

    public Actor bindForUpdate(Actor actor, BindingResult binding)
    {
        // Hibernate is in the dirty habit of automatically persisting any managed entities
        // at the end of the transaction, even if it was never saved. An attacker can force
        // the system to load a managed entity using a parameter (which gets parsed by the converter which loads the entity),
        // which can later be modified by the bound model attributes before our code is even called.
        // We're not going to save this entity, so detach it just in case.
        entityManager.detach(actor);

        Actor oldActor = getById(actor.getId());
        CheckUtils.checkSameVersion(actor, oldActor);

        oldActor.setName(actor.getName());
        oldActor.setSurname(actor.getSurname());
        oldActor.setEmail(actor.getEmail());
        oldActor.setPhoneNumber(actor.getPhoneNumber());
        oldActor.setPostalAddress(actor.getPostalAddress());
        oldActor.setPictureUrl(actor.getPictureUrl());

        validator.validate(oldActor, binding);
        if (binding.hasErrors()) entityManager.detach(oldActor);

        return oldActor;
    }
}
