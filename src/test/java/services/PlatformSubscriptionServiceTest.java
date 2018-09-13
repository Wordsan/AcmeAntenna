package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import domain.CreditCard;
import domain.PlatformSubscription;
import domain.User;
import exceptions.OverlappingPlatformSubscriptionException;
import repositories.PlatformRepository;
import repositories.PlatformSubscriptionRepository;
import utilities.AbstractTest;

/**
 * Tests the following use cases from the requirements document:
 * - Subscribe to a platform for a period of time by providing a valid credit card. The
 * subscription results in a key code that must be provided by the system.
 */
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PlatformSubscriptionServiceTest extends AbstractTest {
    @Autowired private PlatformSubscriptionService platformSubscriptionService;
    @Autowired private PlatformSubscriptionRepository platformSubscriptionRepository;
    @Autowired private PlatformRepository platformRepository;

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    // Test succesful creation of a subscription.
    @Test
    public void testCreateSubscription() throws Exception
    {
        authenticate("user1");

        PlatformSubscription platformSubscription = new PlatformSubscription();

        platformSubscription.setUser((User) getPrincipal());
        platformSubscription.setPlatform(platformRepository.findByName("Atresmedia"));
        platformSubscription.setStartDate(DATE_FORMAT.parse("01/07/2018"));
        platformSubscription.setEndDate(DATE_FORMAT.parse("31/07/2018"));
        platformSubscription.setCreditCard(CreditCard.visaTestCard());
        platformSubscription.setKeyCode("");
        platformSubscription = platformSubscriptionService.create(platformSubscription);

        // Flush changes.
        flushTransaction();

        // Ensure it is created.
        PlatformSubscription platformSubscription2 = platformSubscriptionRepository.findOne(platformSubscription.getId());
        Assert.isTrue(platformSubscription2 != null);
        Assert.isTrue(platformSubscription2 != platformSubscription);
        Assert.isTrue(platformSubscription.equals(platformSubscription2));

        // Ensure keycode is generated.
        Assert.isTrue(platformSubscription2.getKeyCode().length() == 32);
    }

    // Test creation of a subscription fails if start date is after end date.
    @Test(expected = ConstraintViolationException.class)
    public void testCreateSubscriptionFailsIfStartDateAfterEndDate() throws Exception
    {
        authenticate("user1");

        PlatformSubscription platformSubscription = new PlatformSubscription();

        platformSubscription.setUser((User) getPrincipal());
        platformSubscription.setPlatform(platformRepository.findByName("Atresmedia"));
        platformSubscription.setStartDate(DATE_FORMAT.parse("31/07/2018"));
        platformSubscription.setEndDate(DATE_FORMAT.parse("01/07/2018"));
        platformSubscription.setCreditCard(CreditCard.visaTestCard());
        platformSubscription.setKeyCode("");
        platformSubscriptionService.create(platformSubscription);

        // Hibernate only validates on flush, so flush.
        flushTransaction();

        // Shouldn't reach here.
    }

    // Test creation of a subscription fails if making it for other user.
    @Test(expected = AccessDeniedException.class)
    public void testCreateSubscriptionFailsIfWrongPrincipal() throws Exception
    {
        authenticate("user1");

        PlatformSubscription platformSubscription = new PlatformSubscription();

        platformSubscription.setUser((User) getActor("user2"));
        platformSubscription.setPlatform(platformRepository.findByName("Atresmedia"));
        platformSubscription.setStartDate(DATE_FORMAT.parse("01/07/2018"));
        platformSubscription.setEndDate(DATE_FORMAT.parse("31/07/2018"));
        platformSubscription.setCreditCard(CreditCard.visaTestCard());
        platformSubscription.setKeyCode("");
        platformSubscriptionService.create(platformSubscription);

        // Shouldn't reach here.
    }

    // Test creation of a subscription fails if it overlaps with another subscription
    @Test(expected = OverlappingPlatformSubscriptionException.class)
    public void testCreateSubscriptionFailsIfOverlapsWithAnotherSubscription() throws Exception
    {
        authenticate("user1");

        // "user1" already has a subscription with "CNN" around those dates.

        PlatformSubscription platformSubscription = new PlatformSubscription();

        platformSubscription.setUser((User) getPrincipal());
        platformSubscription.setPlatform(platformRepository.findByName("CNN"));
        platformSubscription.setStartDate(DATE_FORMAT.parse("01/07/2018"));
        platformSubscription.setEndDate(DATE_FORMAT.parse("31/07/2018"));
        platformSubscription.setCreditCard(CreditCard.visaTestCard());
        platformSubscription.setKeyCode("");
        platformSubscriptionService.create(platformSubscription);

        // Shouldn't reach here.
    }
}
