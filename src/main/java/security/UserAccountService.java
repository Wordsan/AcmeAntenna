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

    @Autowired private UserAccountRepository repository;


    public UserAccount findOne(final int id)
    {
        return this.repository.findOne(id);
    }

    public UserAccount findByName(final String username)
    {
        return this.repository.findByUsername(username);
    }

    public UserAccount create(final String username, final String password, final String authority) throws ResourceNotUniqueException
    {
        if (this.findByName(username) != null) {
            throw new ResourceNotUniqueException();
        }

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

    public UserAccount updatePassword(final UserAccount userAccount, final String password)
    {
        CheckUtils.checkExists(userAccount);
        final UserAccount currentAccount = this.findOne(userAccount.getId());
        CheckUtils.checkSameVersion(userAccount, currentAccount);

        currentAccount.setPassword(new Md5PasswordEncoder().encodePassword(password, null));
        return this.repository.save(currentAccount);
    }

    public boolean passwordMatchesAccount(final UserAccount account, final String password)
    {
        return new Md5PasswordEncoder().isPasswordValid(account.getPassword(), password, null);
    }

}
