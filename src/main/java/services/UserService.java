package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsAwareConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import domain.Actor;
import domain.User;
import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.CheckUtils;

@Service
@Transactional
public class UserService {
	@Autowired UserRepository repository;
	@Autowired UserAccountService userAccountService;

	public User findPrincipal()
	{
		UserAccount userAccount = LoginService.getPrincipal();
		if (userAccount == null) return null;

		return repository.findByUserAccount(userAccount);
	}

	public User getPrincipal()
	{
		User principal = findPrincipal();
		Assert.notNull(principal);
		return principal;
	}


	public User createAsNewUser(User user)
	{
		CheckUtils.checkUnauthenticated();
		user.setUserAccount(userAccountService.create(user.getUserAccount().getUsername(), user.getUserAccount().getPassword(), Authority.USER));

		return repository.save(user);
	}

}