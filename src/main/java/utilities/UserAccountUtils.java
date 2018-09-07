package utilities;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import security.UserAccount;

public class UserAccountUtils {
    public static void setSessionAccount(UserAccount account)
    {
        if (account == null) {
            SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("" + new Random().nextLong(), "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
            return;
        }
        try {
            PreAuthenticatedAuthenticationToken token
                    = new PreAuthenticatedAuthenticationToken(
                    account,
                    null,
                    account.getAuthorities()
            );

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(
                    token);
            request.getSession().setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
            );

        } catch (Throwable oops) {
            SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("" + new Random().nextLong(), "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));

            throw oops;
        }
    }
}
