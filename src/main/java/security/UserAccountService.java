
package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import utilities.CheckUtils;
import exceptions.UsernameNotUniqueException;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	UserAccountRepository	repository;


	public UserAccount findOne(final int id) {
		return this.repository.findOne(id);
	}

	public UserAccount findByName(final String username) {
		return this.repository.findByUsername(username);
	}

	public UserAccount create(final String username, final String password, final String authority) throws UsernameNotUniqueException {
		if (this.findByName(username) != null)
			throw new UsernameNotUniqueException();

		UserAccount account = new UserAccount();
		account.setUsername(username);
		account.setPassword(new Md5PasswordEncoder().encodePassword(password, null));

		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(authority);
		authorities.add(auth);
		account.setAuthorities(authorities);
		account = this.repository.save(account);

		Assert.notNull(account);
		return account;
	}

	public UserAccount updatePassword(final UserAccount userAccount, final String password) {
		CheckUtils.checkExists(userAccount);
		final UserAccount currentAccount = this.findOne(userAccount.getId());
		CheckUtils.checkSameVersion(userAccount, currentAccount);

		currentAccount.setPassword(new Md5PasswordEncoder().encodePassword(password, null));
		return this.repository.save(currentAccount);
	}

	public boolean passwordMatchesAccount(final UserAccount account, final String password) {
		return new Md5PasswordEncoder().isPasswordValid(account.getPassword(), password, null);
	}

	public UserAccount create() {
		UserAccount res;
		res = new UserAccount();
		final Authority authority = new Authority();
		final List<Authority> authorities = new ArrayList<Authority>();
		authority.setAuthority(Authority.HANDYWORKER);
		authorities.add(authority);
		res.setAuthorities(authorities);

		return res;
	}

}
