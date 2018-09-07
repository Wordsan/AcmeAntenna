package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.Date;

import javax.transaction.Transactional;

import domain.Tutorial;
import domain.User;
import repositories.TutorialRepository;
import utilities.AbstractTest;

/**
 * Tests the following use cases from the requirements document:
 * An actor who is not authenticated must be able to:
 * - List the tutorials in the system and display them, [...].
 * An actor who is authenticated as a user must be able to:
 * - List the tutorials and display them, [...].
 * - Publish a tutorial and edit it.
 * An actor who is authenticated as an administrator must be able to:
 * - Remove a tutorial that he or she thinks is inappropriate.
 */
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TutorialServiceTest extends AbstractTest {
    @Autowired private TutorialService tutorialService;
    @Autowired private TutorialRepository tutorialRepository;

    // Test successful creation of a tutorial.
    @Test
    public void testCreateTutorial()
    {
        authenticate("user1");

        // Make a tutorial.
        Tutorial tutorial = new Tutorial();
        tutorial.setUser((User) getPrincipal());
        tutorial.setTitle("TEST-testCreateTutorial");
        tutorial.setText("My text");
        tutorial.setLastUpdateTime(new Date(1)); // Purposefully wrong. SHould be set by the service.

        // Create it.
        tutorialService.create(tutorial);

        // Flush changes.
        flushTransaction();

        // Ensure it is there.
        tutorial = tutorialRepository.findByTitle("TEST-testCreateTutorial");
        Assert.notNull(tutorial);

        // Test last update time got set.
        Assert.isTrue(Math.abs(tutorial.getLastUpdateTime().getTime() - new Date().getTime()) < 5000);
    }

    // Test successful update of a tutorial.
    @Test
    public void testUpdateTutorial()
    {
        authenticate("user1");

        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        tutorial.setText("TEST-testUpdateTutorial");
        tutorialService.update(tutorial);

        // Flush changes.
        flushTransaction();

        // Ensure it is there.
        tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        Assert.notNull(tutorial);

        // Test changes got done.
        Assert.isTrue(tutorial.getText().equals("TEST-testUpdateTutorial"));

        // Test last update time got set.
        Assert.isTrue(Math.abs(tutorial.getLastUpdateTime().getTime() - new Date().getTime()) < 5000);
    }

    // Test update of a tutorial fails if principal is not owner of tutorial.
    @Test(expected = AccessDeniedException.class)
    public void testUpdateTutorialFailsIfPrincipalNotOwner()
    {
        authenticate("user2");

        // This tutorial belongs to user1, not user2.
        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        tutorial.setText("TEST-testUpdateTutorial");
        tutorialService.update(tutorial);

        // Shouldn't reach this point.
    }

    // Test update of a tutorial fails if principal is not owner of tutorial even if principal is admin.
    @Test(expected = AccessDeniedException.class)
    public void testUpdateTutorialFailsIfPrincipalNotOwnerEvenIfAdmin()
    {
        authenticate("admin");

        // This tutorial belongs to user1, not user2.
        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        tutorial.setText("TEST-testUpdateTutorial");
        tutorialService.update(tutorial);

        // Shouldn't reach this point.
    }

    // Test successful deletion of a tutorial.
    @Test
    public void testDeleteTutorial()
    {
        authenticate("admin");

        // Get a tutorial.
        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");

        // Delete it.
        tutorialService.delete(tutorial.getId());

        // Flush changes.
        flushTransaction();

        // Ensure it's gone.
        tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        Assert.isTrue(tutorial == null);
    }

    // Test deletion of a tutorial fails if not admin.
    @Test(expected = AccessDeniedException.class)
    public void testDeleteTutorialFailsIfNotAdmin()
    {
        authenticate("user1");

        // Get a tutorial.
        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");

        // Try to delete it. user1 is the owner of the tutorial but he's not an admin so this should fail.
        tutorialService.delete(tutorial.getId());

        // Shouldn't reach this point.
    }
}
