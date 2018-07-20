package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import domain.Actor;
import exceptions.OldPasswordDoesntMatchException;
import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.CheckUtils;

@Service
@Transactional
public class ActorService {
	@Autowired private ActorRepository repository;
	@Autowired private UserAccountService userAccountService;

	public Actor findPrincipal()
	{
		if (!LoginService.isAuthenticated()) return null;

		UserAccount userAccount = LoginService.getPrincipal();
		if (userAccount == null) return null;

		return repository.findByUserAccount(userAccount);
	}

	public Actor getPrincipal()
	{
		CheckUtils.checkAuthenticated();
		Actor principal = findPrincipal();
		Assert.notNull(principal);
		return principal;
	}

	Actor findByUsername(String username)
	{
		return repository.findByUsername(username);
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

	public Actor updateOwnPassword(Actor actor, String oldPassword, String newPassword) throws OldPasswordDoesntMatchException
	{
		CheckUtils.checkAuthenticated();
		Actor currentActor = getPrincipal();
		CheckUtils.checkEquals(currentActor, actor);
		CheckUtils.checkSameVersion(actor, currentActor);

		if (!userAccountService.passwordMatchesAccount(actor.getUserAccount(), oldPassword)) {
			throw new OldPasswordDoesntMatchException();
		}

		currentActor.setUserAccount(userAccountService.updatePassword(currentActor.getUserAccount(), newPassword));
		return repository.save(currentActor);
	}
}