
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.CheckUtils;
import domain.Actor;
import exceptions.OldPasswordDoesntMatchException;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository		repository;
	@Autowired
	private UserAccountService	userAccountService;


	public Actor findPrincipal() {
		if (!LoginService.isAuthenticated())
			return null;

		final UserAccount userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			return null;

		return this.repository.findByUserAccount(userAccount);
	}

	public Actor getPrincipal() {
		CheckUtils.checkAuthenticated();
		final Actor principal = this.findPrincipal();
		Assert.notNull(principal);
		return principal;
	}

	public Actor findByUsername(final String username) {
		return this.repository.findByUsername(username);
	}
	public Actor getByUsername(final String username) {
		final Actor actor = this.findByUsername(username);
		Assert.notNull(actor);
		return actor;
	}

	public Actor updateOwnProfile(final Actor submittedActor) {
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

	public Actor updateOwnPassword(final Actor actor, final String oldPassword, final String newPassword) throws OldPasswordDoesntMatchException {
		CheckUtils.checkAuthenticated();
		final Actor currentActor = this.getPrincipal();
		CheckUtils.checkEquals(currentActor, actor);
		CheckUtils.checkSameVersion(actor, currentActor);

		if (!this.userAccountService.passwordMatchesAccount(actor.getUserAccount(), oldPassword))
			throw new OldPasswordDoesntMatchException();

		currentActor.setUserAccount(this.userAccountService.updatePassword(currentActor.getUserAccount(), newPassword));
		return this.repository.save(currentActor);
	}

	public Collection<Actor> findAll() {
		final Collection<Actor> res = this.repository.findAll();
		return res;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);
		final Actor a = this.repository.findOne(actorId);
		Assert.notNull(a);
		return a;
	}

	public Actor save(final Actor a) {
		Assert.notNull(a);
		final Actor res = this.repository.save(a);
		Assert.notNull(res);
		return res;
	}
}
