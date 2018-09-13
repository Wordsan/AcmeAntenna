package useCases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import domain.Actor;
import domain.Banner;
import exceptions.ResourceNotFoundException;
import services.ActorService;
import services.AdministratorService;
import services.AntennaService;
import services.BannerService;
import services.HandyworkerService;
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
public class AdminUseCases extends AbstractTest {

    @Autowired
    private ActorService actorService;

    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private HandyworkerService handyworkerService;
    @Autowired
    private AntennaService antennaService;
    @Autowired
    private BannerService bannerService;


    //Ban/unban another actor.
    //Banning an actor being an admin
    @Test
    public void BanAnUserBeingAnAdmin()
    {
        this.unauthenticate();
        //Auth as an admin
        this.authenticate("admin");
        final List<Actor> actors = new ArrayList<Actor>(this.actorService.findAll());
        //A random actor is banned
        this.actorService.setBanned(actors.get(4).getId(), true);
        //Checking if that actor that we select previously is now banned
        Assert.isTrue(this.actorService.findById(actors.get(4).getId()).getBanned());

    }

    //Ban/unban another actor.
    //test getting the requests already serviced from an user
    @Test(expected = AccessDeniedException.class)
    public void BanAnUserNotBeingAnAdmin() throws IllegalArgumentException
    {
        this.unauthenticate();
        //Auth as an user
        this.authenticate("user1");
        this.actorService.setBanned(5, true);

    }

    //Ban/unban another actor.
    //Banning a non existing actor
    @Test(expected = ResourceNotFoundException.class)
    public void BanAnNonExistingId() throws IllegalArgumentException
    {
        this.unauthenticate();
        //Auth as an admin
        this.authenticate("admin");
        this.actorService.setBanned(-1, true);

    }

    //Admin: Remove banners that he or she thinks are inappropriate.
    @Test
    public void RemoveBanner()
    {
        this.unauthenticate();
        //Auth as an admin
        this.authenticate("admin");
        final List<Banner> banners = new ArrayList<Banner>(this.bannerService.findAll());
        this.bannerService.delete(banners.get(0).getId());
        Assert.isTrue(!this.bannerService.findAll().contains(banners.get(0)));

    }

    //Admin: Remove banners that he or she thinks are inappropriate.
    //Remove a banner being an user
    @Test(expected = AccessDeniedException.class)
    public void RemoveBannerBeingUser() throws IllegalArgumentException
    {
        this.unauthenticate();
        //Auth as an user
        this.authenticate("user1");
        final List<Banner> banners = new ArrayList<Banner>(this.bannerService.findAll());
        this.bannerService.delete(banners.get(0).getId());
        Assert.isTrue(!this.bannerService.findAll().contains(banners.get(0)));

    }

    //Admin: Remove banners that he or she thinks are inappropriate.
    //Remove a non existing banner
    @Test(expected = AccessDeniedException.class)
    public void RemoveANullBanner() throws IllegalArgumentException
    {
        this.unauthenticate();
        //Auth as an user
        this.authenticate("user1");

        this.bannerService.delete(-1);

    }

}
