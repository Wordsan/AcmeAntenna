package services;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.xml.stream.events.Comment;

import domain.Platform;
import domain.Satellite;
import domain.Tutorial;
import domain.TutorialComment;
import domain.User;
import repositories.PlatformRepository;
import repositories.SatelliteRepository;
import repositories.TutorialCommentRepository;
import repositories.TutorialRepository;
import sun.awt.image.SurfaceManager;
import utilities.AbstractTest;

/**
 * Tests the following use cases from the requirements document:
 *  An actor who is not authenticated must be able to:
 *  - List the tutorials in the system and display them, **but not their comments**.
 *  An actor who is authenticated as a user must be able to:
 *  - List the tutorials and display them, **including their comments**.
 *  - Post a comment to a tutorial. Confirmation must be requested before posting a
 *    comment since they cannot be modified later.
 *  An actor who is authenticated as an administrator must be able to:
 *  - Remove a comment that he or she thinks is inappropriate.
 */
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TutorialCommentServiceTest extends AbstractTest {
    @Autowired private TutorialCommentService tutorialCommentService;
    @Autowired private TutorialCommentRepository tutorialCommentRepository;
    @Autowired private TutorialRepository tutorialRepository;

    // Test authenticated users can see comments.
    @Test
    public void testCommentList()
    {
        authenticate("user2");

        // Get a tutorial.
        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        Assert.notNull(tutorial);

        // Get comments.
        Page<TutorialComment> comments = tutorialCommentService.findAllForTutorial(tutorial, new PageRequest(0, 10));

        // Ensure we have comments.
        Assert.isTrue(!comments.getContent().isEmpty());
    }

    // Test unauthenticated users cannot see comments.
    @Test(expected = AccessDeniedException.class)
    public void testCommentListFailsIfUnauthenticated()
    {
        unauthenticate();

        // Get a tutorial.
        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        Assert.notNull(tutorial);

        tutorialCommentService.findAllForTutorial(tutorial, new PageRequest(0, 10));

        // Shouldn't reach this point.
    }

    // Test successful creation of a comment.
    @Test
    public void testCreateComment()
    {
        authenticate("user1");

        // Get a tutorial.
        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        Assert.notNull(tutorial);

        // Create a comment for this tutorial.
        TutorialComment comment = new TutorialComment();
        comment.setUser((User) getPrincipal());
        comment.setTutorial(tutorial);
        comment.setCreationTime(new Date(1)); // Purposefully wrong. Should be set by the service.
        comment.getPictureUrls().add("http://example.com/picture.png");
        comment.setTitle("My title");
        comment.setText(StringUtils.repeat("My text", 1000)); // Very long text.

        comment = tutorialCommentService.create(comment);

        // Flush changes.
        flushTransaction();

        // Reload tutorial.
        tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");

        // Reload comment.
        comment = tutorialCommentRepository.findOne(comment.getId());

        // Ensure tutorial contains comment.
        Assert.isTrue(tutorial.getTutorialComments().contains(comment));

        // Ensure creation time is correct.
        Assert.isTrue(Math.abs(comment.getCreationTime().getTime() - new Date().getTime()) < 5000);

        // Ensure text did not get truncated by the database.
        Assert.isTrue(comment.getText().equals(StringUtils.repeat("My text", 1000)));
    }

    // Test creation of a comment fails if principal does not match.
    @Test(expected = AccessDeniedException.class)
    public void testCreateCommentFailsWithNonMatchingPrincipal()
    {
        authenticate("user1");

        // Get a tutorial.
        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        Assert.notNull(tutorial);

        // Create a comment for this tutorial.
        TutorialComment comment = new TutorialComment();
        comment.setTutorial(tutorial);
        comment.setTitle("My title");
        comment.setText("My text");
        comment.setUser((User) getActor("user2"));

        comment = tutorialCommentService.create(comment);

        // Shouldn't reach this point.
    }

    // Test creation of a comment fails if title is empty.
    @Test(expected = ConstraintViolationException.class)
    public void testCreateCommentFailsWithEmptyTitle()
    {
        authenticate("user1");

        // Get a tutorial.
        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        Assert.notNull(tutorial);

        // Create a comment for this tutorial.
        TutorialComment comment = new TutorialComment();
        comment.setTutorial(tutorial);
        comment.setTitle("");
        comment.setText("My text");
        comment.setUser((User) getPrincipal());

        comment = tutorialCommentService.create(comment);

        // Hibernate doesn't validate until flush, so flush.
        flushTransaction();

        // Shouldn't reach this point.
    }

    // Test successful deletion of a comment.
    @Test
    public void testDeleteComment()
    {
        authenticate("admin");

        // Get a tutorial.
        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        Assert.notNull(tutorial);

        // Get a comment.
        TutorialComment comment = tutorial.getTutorialComments().get(0);
        int id = comment.getId();

        // Delete it.
        tutorialCommentService.delete(comment.getId());

        // Flush changes.
        flushTransaction();

        // Ensure it is not there.
        Assert.isTrue(tutorialCommentRepository.findOne(id) == null);
    }

    // Test deletion of a comment fails if not admin.
    @Test(expected = AccessDeniedException.class)
    public void testDeleteCommentFailsIfNotAdmin()
    {
        authenticate("user1");

        // Get a tutorial.
        Tutorial tutorial = tutorialRepository.findByTitle("(READ!) A beginner guide to Lorem Ipsum");
        Assert.notNull(tutorial);

        // Get a comment.
        TutorialComment comment = tutorial.getTutorialComments().get(0);
        int id = comment.getId();

        // Delete it.
        tutorialCommentService.delete(comment.getId());

        // Shouldn't reach this point.
    }
}
