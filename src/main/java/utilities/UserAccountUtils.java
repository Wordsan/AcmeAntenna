package utilities;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import security.UserAccount;

public class UserAccountUtils {
    public static void setSessionAccount(UserAccount account)
    {
        try {
            PreAuthenticatedAuthenticationToken token
                    = new PreAuthenticatedAuthenticationToken(
                    account,
                    null,
                    account.getAuthorities());

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(
                    token);
            request.getSession().setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());

        } catch (Throwable oops) {
            SecurityContextHolder.getContext().setAuthentication(
                    null);

            throw oops;
        }
    }
}
