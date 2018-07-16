package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import domain.Administrator;
import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {
	@Autowired AdministratorRepository repository;

	public Administrator findPrincipal()
	{
		UserAccount userAccount = LoginService.getPrincipal();
		if (userAccount == null) return null;

		return repository.findByUserAccount(userAccount);
	}

	public Administrator getPrincipal()
	{
		Administrator principal = findPrincipal();
		Assert.notNull(principal);
		return principal;
	}
}