package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

import javax.transaction.Transactional;

import domain.Platform;
import domain.Satellite;
import repositories.PlatformRepository;
import repositories.SatelliteRepository;
import utilities.AbstractTest;

/**
 * Tests the following use cases from the requirements document:
 * - Browse the list of platforms and [...].
 * - Search for satellites and platforms using a single key word that must be contained in
 * their names or descriptions.
 * <p>
 * Also tests the following use case given by email by Mr. Carlos Muller:
 * - An actor who is not authenticated must be able to manage the list of satellites
 * and platforms, which involves listing, registering, editing, and deleting them.
 * <p>
 * The complete email is available in the "Documentacion" folder for reference.
 */
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PlatformServiceTest extends AbstractTest {
    @Autowired private PlatformService platformService;
    @Autowired private PlatformRepository platformRepository;
    @Autowired private SatelliteRepository satelliteRepository;

    // Test successful creation of a platform.
    @Test
    public void testCreatePlatform()
    {
        authenticate("admin");

        // Get a satellite.
        Satellite satellite = satelliteRepository.findByName("METEOSAT");
        Assert.isTrue(satellite != null);

        // Create a platform.
        Platform platform = new Platform();
        platform.setName("TEST-testCreatePlatform");
        platform.setDescription("MY DESCRIPTION");
        platform.getSatellites().add(satellite);
        platform.setDeleted(false);
        platform = platformService.create(platform);

        // Flush changes.
        flushTransaction();

        // Check that it's really created.
        Platform platform2 = platformRepository.findByName("TEST-testCreatePlatform");
        Assert.isTrue(platform2 != null);
        Assert.isTrue(platform != platform2);
        Assert.isTrue(platform.equals(platform2));

        // Check it has the satellite we added.
        Assert.isTrue(platform2.getSatellites().contains(satellite));
    }

    // Test creation of a platform fails if principal is not an administrator.
    @Test(expected = AccessDeniedException.class)
    public void testCreatePlatformFailsWithNonAdminPrincipal()
    {
        authenticate("user1");

        // Create a platform.
        Platform platform = new Platform();
        platform.setName("TEST-testCreatePlatformFailsWithNonAdminPrincipal");
        platform.setDescription("MY DESCRIPTION");
        platform.setDeleted(false);
        platformService.create(platform);

        // Shouldn't reach this point.
    }

    // Test creation of a platform fails if the platform is already marked as deleted on creation.
    @Test(expected = AccessDeniedException.class)
    public void testCreatePlatformFailsWithDeletedPlatform()
    {
        authenticate("admin");

        // Create a platform.
        Platform platform = new Platform();
        platform.setName("TEST-testCreatePlatformFailsWithDeletedPlatform");
        platform.setDescription("MY DESCRIPTION");
        platform.setDeleted(true);
        platformService.create(platform);

        // Shouldn't reach this point.
    }

    // Test successful update of a platform.
    @Test
    public void testUpdatePlatform()
    {
        authenticate("admin");

        // Get a platform.
        Platform platform = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform != null);

        // Change something.
        platform.setName("TEST-testUpdatePlatform");

        // Update this platform.
        platformService.update(platform);

        // Flush changes.
        flushTransaction();

        // Check that it's really updated.
        Platform platform2 = platformRepository.findByName("TEST-testUpdatePlatform");
        Assert.isTrue(platform2 != null);
        Assert.isTrue(platform != platform2);
        Assert.isTrue(platform.equals(platform2));
    }

    // Test successful update of a platform. (2)
    // As the platform isn't the owner side of the relationship, adding or removing a satellite is slightly more complicated
    // than on the satellite side. Ensure it is working correctly.
    @Test
    public void testUpdatePlatformAddSat()
    {
        authenticate("admin");

        // Get a platform.
        Platform platform = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform != null);

        // Get a satellite.
        Satellite satellite = satelliteRepository.findByName("METEOSAT");
        Assert.isTrue(satellite != null);

        // Ensure this satellite is not already assigned to the platform.
        Assert.isTrue(!platform.getSatellites().contains(satellite));

        // Add this satellite to the platform.
        platform.getSatellites().add(satellite);

        // Update this platform.
        platformService.update(platform);

        // Flush changes.
        flushTransaction();

        // Check that it's really updated.
        Platform platform2 = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform2 != null);
        Assert.isTrue(platform != platform2);
        Assert.isTrue(platform.equals(platform2));
        Assert.isTrue(platform2.getSatellites().contains(satellite));
    }

    // Test successful update of a platform. (3)
    // As the platform isn't the owner side of the relationship, adding or removing a satellite is slightly more complicated
    // than on the satellite side. Ensure it is working correctly.
    @Test
    public void testUpdatePlatformRemoveSat()
    {
        authenticate("admin");

        // Get a platform.
        Platform platform = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform != null);

        // Get a satellite.
        Satellite satellite = satelliteRepository.findByName("ENGLAND-SAT");
        Assert.isTrue(satellite != null);

        // Ensure this satellite is already assigned to the platform.
        Assert.isTrue(platform.getSatellites().contains(satellite));

        // Add this satellite to the platform.
        platform.getSatellites().remove(satellite);

        // Update this platform.
        platformService.update(platform);

        // Flush changes.
        flushTransaction();

        // Check that it's really updated.
        Platform platform2 = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform2 != null);
        Assert.isTrue(platform != platform2);
        Assert.isTrue(platform.equals(platform2));
        Assert.isTrue(!platform2.getSatellites().contains(satellite));
    }

    // Test update of a platform fails if principal is not an administrator.
    @Test(expected = AccessDeniedException.class)
    public void testUpdatePlatformFailsIfNotAdmin()
    {
        authenticate("user1");

        // Get a platform.
        Platform platform = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform != null);

        // Change something.
        platform.setName("TEST-testUpdatePlatformFailsIfNotAdmin");

        // Update this platform.
        platformService.update(platform);

        // Shouldn't reach this point.
    }

    // Test update of a platform fails if an attempt to add a deleted satellite is made.
    @Test(expected = AccessDeniedException.class)
    public void testUpdatePlatformFailsWithDeletedSatellite()
    {
        authenticate("admin");

        // Get a platform.
        Platform platform = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform != null);

        // Get a deleted satellite.
        Satellite satellite = satelliteRepository.findByName("DELETED-SAT");
        Assert.isTrue(satellite != null);

        // Add it to the platform.
        platform.getSatellites().add(satellite);

        // Update this platform.
        platformService.update(platform);

        // Shouldn't reach this point.
    }

    // Test update of a platform fails if platform was deleted.
    @Test(expected = AccessDeniedException.class)
    public void testUpdatePlatformFailsWithDeletedPlatform()
    {
        authenticate("admin");

        // Get a platform.
        Platform platform = platformRepository.findByName("DELETED-PLATFORM");
        Assert.isTrue(platform != null);

        // Change something.
        platform.setName("asdfasdf");

        // Update this platform.
        platformService.update(platform);

        // Shouldn't reach this point.
    }

    // Test update of a platform fails if an attempt to set the deleted flag is made.
    // The deleted flag should only be set by using the delete method.
    @Test(expected = AccessDeniedException.class)
    public void testUpdatePlatformFailsToDeletePlatform()
    {
        authenticate("admin");

        // Get a platform.
        Platform platform = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform != null);

        // Set the deleted flag.
        platform.setDeleted(true);

        // Update this platform.
        platformService.update(platform);

        // Shouldn't reach this point.
    }

    // Test successful deletion of a platform.
    @Test
    public void testDeletePlatform()
    {
        authenticate("admin");

        // Get a platform.
        Platform platform = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform != null);

        // It is not deleted yet.
        Assert.isTrue(!platform.getDeleted());

        // Delete this platform.
        platformService.delete(platform.getId());

        // Flush changes.
        flushTransaction();

        // Check that it's really deleted.
        Platform platform2 = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform2 != null);
        Assert.isTrue(platform != platform2);
        Assert.isTrue(platform.equals(platform2));

        Assert.isTrue(platform2.getDeleted());

        // Check that it has no satellites associated.
        Assert.isTrue(platform2.getSatellites().isEmpty());

    }

    // Test deletion of a platform fails if not admin.
    @Test(expected = AccessDeniedException.class)
    public void testDeletePlatformFailsIfNotAdmin()
    {
        authenticate("user1");

        // Get a platform.
        Platform platform = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform != null);

        // It is not deleted yet.
        Assert.isTrue(!platform.getDeleted());

        // Delete this platform.
        platformService.delete(platform.getId());

        // Shouldn't reach this point.
    }


    // Test deletion of a platform fails if already deleted.
    @Test(expected = AccessDeniedException.class)
    public void testDeletePlatformFailsIfAlreadyDeleted()
    {
        authenticate("admin");

        // Get a platform.
        Platform platform = platformRepository.findByName("DELETED-PLATFORM");
        Assert.isTrue(platform != null);

        // It is deleted already.
        Assert.isTrue(platform.getDeleted());

        // Delete this platform.
        platformService.delete(platform.getId());

        // Shouldn't reach this point.
    }

    // Test listing of platforms.
    public void testPlatformIndex()
    {
        // No authentication required for this.
        unauthenticate();

        // Get listing.
        List<Platform> platforms = platformService.findAllForIndex();

        // All platforms should appear.
        Platform platformThatShouldAppear = platformRepository.findByName("Atresmedia");

        // Unless they are deleted.
        Platform platformThatShouldntAppear = platformRepository.findByName("DELETED-PLATFORM");

        Assert.isTrue(platforms.contains(platformThatShouldAppear));
        Assert.isTrue(!platforms.contains(platformThatShouldntAppear));
    }

    // Test search of platforms.
    public void testPlatformSearch()
    {
        // No authentication required for this.
        unauthenticate();

        // Search for "Network".
        List<Platform> platforms = platformService.search("network");

        // All platforms shown should contain the word network in the name..
        for (Platform platform : platforms) {
            Assert.isTrue(platform.getName().toLowerCase().contains("network"));
        }

        // Search for "deleted".
        platforms = platformService.search("deleted");

        // The deleted platform should not appear as it is deleted.
        Assert.isTrue(platforms.isEmpty());

        // A common mistake with Lucene is not handling the exception that occurs
        // when only a extremely common word is used, such as "this".
        //
        // This happens because Lucene does not index very common words and removes them from the query,
        // if the query is empty as a result an exception is thrown.
        //
        // Ensure nothing is thrown when a search for "this" is made.
        platforms = platformService.search("this");

        // In that case, the full listing should be shown.
        Assert.isTrue(platforms.size() == platformService.findAllForIndex().size());
    }
}
