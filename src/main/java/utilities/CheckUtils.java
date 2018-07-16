package utilities;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.Assert;

import java.text.MessageFormat;

import javax.persistence.OptimisticLockException;

import domain.Actor;
import domain.DomainEntity;
import security.Authority;
import security.LoginService;
import security.UserAccount;

public class CheckUtils {
    public static void checkTrue(boolean value)
    {
        if (!value) {
            throw new AccessDeniedException("Security check failed: Expecting true, got false");
        }
    }

    public static void checkFalse(boolean value)
    {
        if (value) {
            throw new AccessDeniedException("Security check failed: Expecting false, got true");
        }
    }

    public static void checkExists(DomainEntity entity)
    {
        boolean authorized = false;

        if (entity != null && entity.getId() != 0) {
            authorized = true;
        }

        if (!authorized) {
            throw new AccessDeniedException("Security check failed: Expecting an entity existing in the DB, got new entity instead");
        }
    }

    public static void checkNotExists(DomainEntity entity)
    {
        boolean authorized = true;

        if (entity != null && entity.getId() != 0) {
            authorized = false;
        }

        if (!authorized) {
            throw new AccessDeniedException("Security check failed: Expecting a new entity, got entity already existing in the DB instead");
        }
    }

    public static <T> void checkEquals(T a, T b)
    {
        boolean equals = a == null && b == null;
        equals = equals || (a != null && b != null && a.equals(b));
        if (!equals) {
            throw new AccessDeniedException(MessageFormat.format(
                    "Security check failed: {0} does not equal {1}",
                    a != null ? a.toString() : "null",
                    b != null ? b.toString() : "null"
            ));
        }
    }

    public static void checkAuthenticated()
    {
        if (!LoginService.isAuthenticated()) {
            throw new AccessDeniedException("Security check failed: Expecting principal would be authenticated, but it is not");
        }
    }

    public static void checkUnauthenticated()
    {
        if (LoginService.isAuthenticated()) {
            throw new AccessDeniedException("Security check failed: Expecting principal would be unauthenticated, but it is");
        }
    }

    public static void checkIsPrincipal(Actor actor)
    {
        boolean authorized = false;

        UserAccount principal = LoginService.getPrincipal();
        UserAccount actorAccount = actor != null ? actor.getUserAccount() : null;

        if (principal == actorAccount) authorized = true;
        if (principal != null && actorAccount != null && principal.equals(actorAccount)) authorized = true;

        if (!authorized) {
            throw new AccessDeniedException(MessageFormat.format(
                    "Security check failed: Expecting {0} would be the principal, but the principal is {1}",
                    actorAccount != null ? (actorAccount.getUsername() != null ? actorAccount.getUsername() : "null") : "null",
                    principal != null ? (principal.getUsername() != null ? principal.getUsername() : "null") : "null"
            ));
        }
    }

    public static void checkPrincipalAuthority(String... expectedAuthorities)
    {
        if (!LoginService.isAuthenticated()) {
            throw new AccessDeniedException(MessageFormat.format(
                    "Security check failed: Expecting principal with one of the authorities: [{0}], but principal is unauthenticated",
                    StringUtils.join(expectedAuthorities, ", ")
            ));
        }

        UserAccount account = LoginService.getPrincipal();
        Assert.notNull(account);

        boolean authorized = false;

        for (String authorityExpected: expectedAuthorities) {
            for (Authority accountAuthority : account.getAuthorities()) {
                if (accountAuthority.getAuthority().equals(authorityExpected)) {
                    authorized = true;
                    break;
                }
            }
            if (authorized) break;
        }

        if (!authorized) {
            throw new AccessDeniedException(MessageFormat.format(
                    "Security check failed: Expecting actor with one of the authorities: [{0}], got actor with authorities: [{1}]",
                    StringUtils.join(expectedAuthorities, ", "),
                    StringUtils.join(account.getAuthorities(), ", ")
            ));
        }
    }

    public static void checkSameVersion(DomainEntity entity1, DomainEntity entity2)
    {
        if (entity1.getVersion() != entity2.getVersion()) {
            throw new OptimisticLockException(MessageFormat.format("Security check failed: Expecting version {0}, but got version {1}", entity1.getVersion(), entity2.getVersion()));
        }
    }
}
