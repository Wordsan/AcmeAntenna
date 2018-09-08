package useCases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import domain.Banner;
import services.AgentService;
import services.BannerService;
import services.HandyworkerService;
import services.UserService;
import utilities.AbstractTest;

/**
 * Tests the following use cases from the requirements document:
 * - Register to the system as a user.
 */
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterBanner extends AbstractTest {

    @Autowired
    private AgentService agentService;

    @Autowired
    private UserService userService;
    @Autowired
    private HandyworkerService handyworkerService;
    @Autowired
    private BannerService bannerService;


    //Register a banner to the system.
    @Test
    public void RegisterBannerBeingAgent()
    {
        this.unauthenticate();
        final Banner banner = new Banner();
        this.authenticate("agent1");
        banner.setAgent(this.agentService.findPrincipal());
        banner.setPictureUrl("https://i.imgur.com/S4LGdiI.jpg");
        banner.setTargetPage("http://nike.com");
        banner.setCreditCard("4559960410324422");
        final Banner saved = this.bannerService.create(banner);

        Assert.isTrue(this.bannerService.findAll().contains(saved));

    }

    //Register a banner to the system.
    //Registering without being an agent
    @Test(expected = IllegalArgumentException.class)
    public void RegisterBannerBeingAnUser() throws IllegalArgumentException
    {
        this.unauthenticate();
        final Banner banner = new Banner();
        this.authenticate("user1");
        banner.setAgent(this.agentService.findPrincipal());
        banner.setPictureUrl("https://i.imgur.com/S4LGdiI.jpg");
        banner.setTargetPage("http://nike.com");
        banner.setCreditCard("4559960410324422");
        final Banner saved = this.bannerService.create(banner);

        Assert.isTrue(this.bannerService.findAll().contains(saved));

    }

    //Register a banner to the system.
    //Registering a null banner
    @Test(expected = IllegalArgumentException.class)
    public void RegisterBannerWithoutCreditCard() throws IllegalArgumentException
    {
        final Banner saved = this.bannerService.create(null);

        Assert.isTrue(this.bannerService.findAll().contains(saved));

    }

}
