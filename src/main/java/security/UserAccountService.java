package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import exceptions.UsernameNotUniqueException;
import utilities.CheckUtils;

@Service
@Transactional
public class UserAccountService {
	@Autowired
	UserAccountRepository repository;

	public UserAccount findOne(int id)
	{
		return repository.findOne(id);
	}

	public UserAccount findByName(String username)
	{
		return repository.findByUsername(username);
	}

	public Collection<UserAccount> findAll()
	{
		return repository.findAll();
	}

	public UserAccount create(String username,
			String password,
			String authority)
	{
		if (findByName(username) != null) {
			throw new UsernameNotUniqueException();
		}

		UserAccount account = new UserAccount();
		account.setUsername(username);
		account.setPassword(new Md5PasswordEncoder().encodePassword(
					password, null));

		Collection<Authority> authorities = new ArrayList<Authority>();
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

	public void destroy(int id)
	{
		repository.delete(id);
	}
}
