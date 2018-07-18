package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import domain.Actor;
import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.CheckUtils;

@Service
@Transactional
public class ActorService {
	@Autowired ActorRepository repository;
	@Autowired UserAccountService userAccountService;

	public Actor findPrincipal()
	{
		UserAccount userAccount = LoginService.getPrincipal();
		if (userAccount == null) return null;

		return repository.findByUserAccount(userAccount);
	}

	public Actor getPrincipal()
	{
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

	public Actor updateOwnPassword(Actor actor, String password)
	{
		Actor currentActor = getPrincipal();
		CheckUtils.checkEquals(currentActor, actor);
		CheckUtils.checkSameVersion(actor, currentActor);

		currentActor.setUserAccount(userAccountService.updatePassword(currentActor.getUserAccount(), password));
		return repository.save(currentActor);
	}
}