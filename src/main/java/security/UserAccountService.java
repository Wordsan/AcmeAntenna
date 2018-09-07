package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import exceptions.ResourceNotUniqueException;
import utilities.CheckUtils;


@Service
@Transactional
public class UserAccountService {
	@Autowired UserAccountRepository repository;

	public UserAccount findOne(int id)
	{
		return repository.findOne(id);
	}

	public UserAccount findByName(String username)
	{
		return repository.findByUsername(username);
	}


	public UserAccount create(String username,
			String password,
			String authority) throws ResourceNotUniqueException
	{
		if (findByName(username) != null) {
			throw new ResourceNotUniqueException();
		}

		UserAccount account = new UserAccount();
		account.setUsername(username);
		account.setPassword(new Md5PasswordEncoder().encodePassword(
					password, null));

		Collection<Authority> authorities = new ArrayList<>();
		Authority auth = new Authority();
		auth.setAuthority(authority);
		authorities.add(auth);
		account.setAuthorities(authorities);
		account = repository.save(account);

		Assert.notNull(account);
		return account;
	}

	public UserAccount updatePassword(UserAccount userAccount, String password)
	{
		CheckUtils.checkExists(userAccount);
		UserAccount currentAccount = findOne(userAccount.getId());
		CheckUtils.checkSameVersion(userAccount, currentAccount);

		currentAccount.setPassword(new Md5PasswordEncoder().encodePassword(password, null));
		return repository.save(currentAccount);
	}

	public boolean passwordMatchesAccount(UserAccount account, String password)
	{
		return new Md5PasswordEncoder().isPasswordValid(account.getPassword(), password, null);
	}

}
