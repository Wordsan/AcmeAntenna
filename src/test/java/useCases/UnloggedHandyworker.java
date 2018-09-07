package useCases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import domain.Handyworker;
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
public class UnloggedHandyworker extends AbstractTest {

    @Autowired
    private HandyworkerService handyworkerService;


    //Browse the catalogue of handyworkers.
    //Test getting the list of handyworkers
    @Test
    public void testGetHandyworkersUnlogged()
    {
        this.unauthenticate();
        final List<Handyworker> workers = new ArrayList<Handyworker>(this.handyworkerService.findAll());
        Assert.isTrue(!workers.isEmpty());

    }

    //Register to the system as a handyworker.
    //Registering a null Handyworker
    @Test(expected = IllegalArgumentException.class)
    public void registerNullHandyworker() throws IllegalArgumentException
    {
        final Handyworker worker = null;
        this.handyworkerService.save(worker);

    }

    //Register to the system as a handyworker.
    //Registering a Handyworker with a null userAccount
    @Test(expected = IllegalArgumentException.class)
    public void registerHandyworkerWithoutUserAccount() throws IllegalArgumentException
    {
        final Handyworker worker = this.handyworkerService.create();
        worker.setName("Name");
        worker.setSurname("Surname");
        worker.setEmail("email@mail.com");

        worker.setUserAccount(null);
        this.handyworkerService.save(worker);

    }
}
