
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyworkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.CheckUtils;
import domain.Actor;
import domain.Handyworker;
import exceptions.UsernameNotUniqueException;

@Service
@Transactional
public class HandyworkerService {

	@Autowired
	private HandyworkerRepository	handyworkerRepository;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private ActorService			actorService;


	public HandyworkerService() {
		super();
	}

	public Handyworker findPrincipal() {
		final Actor principal = this.actorService.findPrincipal();
		if (principal instanceof Handyworker)
			return (Handyworker) principal;
		return null;
	}

	public Handyworker create() {
		final Handyworker h = new Handyworker();
		return h;
	}

	public Collection<Handyworker> findAll() {
		final Collection<Handyworker> res = this.handyworkerRepository.findAll();
		return res;
	}

	public Handyworker findByPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Handyworker res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Handyworker findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		final Handyworker res = this.handyworkerRepository.findByUserAccount(userAccount.getId());

		return res;
	}

	public Handyworker save(final Handyworker handyworker) {
		Assert.notNull(handyworker);
		final Handyworker res = this.handyworkerRepository.save(handyworker);

		return res;
	}

	public Handyworker createAsNewHandyworker(final Handyworker handyworker) throws UsernameNotUniqueException {
		CheckUtils.checkUnauthenticated();
		CheckUtils.checkNotExists(handyworker);

		handyworker.setUserAccount(this.userAccountService.create(handyworker.getUserAccount().getUsername(), handyworker.getUserAccount().getPassword(), Authority.HANDYWORKER));

		return this.handyworkerRepository.save(handyworker);
	}

}
