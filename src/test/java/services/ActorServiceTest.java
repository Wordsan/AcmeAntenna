package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import domain.Actor;
import exceptions.OldPasswordDoesntMatchException;
import security.UserAccountService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorServiceTest extends AbstractTest {
    @Autowired private ActorService actorService;
    @Autowired private UserAccountService userAccountService;

    // Test actor can update his own profile.
    @Test
    public void testActorCanUpdateOwnProfile()
    {
        authenticate("user1");

        // Change principal's name.
        Actor principal = getPrincipal();
        principal.setName("TEST-testActorCanUpdateOwnProfile");
        actorService.updateOwnProfile(principal);

        // Flush changes.
        flushTransaction();

        // Reload entity.
        Actor principal2 = getPrincipal();
        Assert.isTrue(principal != principal2);

        // Ensure changes are done.
        Assert.isTrue(principal2.getName().equals("TEST-testActorCanUpdateOwnProfile"));
    }

    // Test actor cannot update other people's profiles.
    @Test(expected = AccessDeniedException.class)
    public void testActorCannotUpdateOthersProfiles()
    {
        authenticate("user1");

        // Change other user's profile.
        Actor user = getActor("user2");
        user.setName("TEST-testActorCanUpdateOwnProfile");
        actorService.updateOwnProfile(user);

        // Shouldn't reach this point.
    }

    // Test actor can update his own password.
    @Test
    public void testActorCanUpdateOwnPassword() throws OldPasswordDoesntMatchException
    {
        authenticate("user1");

        // Change principal's password.
        Actor principal = getPrincipal();
        actorService.updateOwnPassword("user1", "new password");

        // Flush changes.
        flushTransaction();

        // Reload entity.
        Actor principal2 = getPrincipal();
        Assert.isTrue(principal != principal2);

        // Ensure changes are done.
        Assert.isTrue(userAccountService.passwordMatchesAccount(principal2.getUserAccount(), "new password"));
    }

    // Test actor cannot update his own password if he provides the wrong current password.
    @Test(expected = OldPasswordDoesntMatchException.class)
    public void testActorCannotUpdateOwnPasswordIfWrongPassword() throws OldPasswordDoesntMatchException
    {
        authenticate("user1");

        // Attempt to change principal's password, providing wrong password.
        actorService.updateOwnPassword("WRONG PASSWORD", "new password");

        // Shouldn't reach this point.
    }

    // Test actor cannot update his own password if he provides the a bad new password.
    @Test(expected = ConstraintViolationException.class)
    public void testActorCannotUpdateOwnPasswordIfBadNewPassword() throws OldPasswordDoesntMatchException
    {
        authenticate("user1");

        // Attempt to change principal's password, providing a bad (too short) new password.
        actorService.updateOwnPassword("user1", "1");

        // Shouldn't reach this point.
    }
}
