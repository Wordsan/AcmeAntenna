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
 * - Browse the list of satellites and [...].
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
public class SatelliteServiceTest extends AbstractTest {
    @Autowired private SatelliteService satelliteService;
    @Autowired private SatelliteRepository satelliteRepository;
    @Autowired private PlatformRepository platformRepository;

    // Test successful creation of a satellite.
    @Test
    public void testCreateSatellite()
    {
        authenticate("admin");

        // Get a platform.
        Platform platform = platformRepository.findByName("Atresmedia");
        Assert.isTrue(platform != null);

        // Create a satellite.
        Satellite satellite = new Satellite();
        satellite.setName("TEST-testCreateSatellite");
        satellite.setDescription("MY DESCRIPTION");
        satellite.getPlatforms().add(platform);
        satellite.setDeleted(false);
        satellite = satelliteService.create(satellite);

        // Flush changes.
        flushTransaction();

        // Check that it's really created.
        Satellite satellite2 = satelliteRepository.findByName("TEST-testCreateSatellite");
        Assert.isTrue(satellite2 != null);
        Assert.isTrue(satellite != satellite2);
        Assert.isTrue(satellite.equals(satellite2));

        // Check it has the satellite we added.
        Assert.isTrue(satellite2.getPlatforms().contains(platform));
    }

    // Test creation of a satellite fails if principal is not an administrator.
    @Test(expected = AccessDeniedException.class)
    public void testCreateSatelliteFailsWithNonAdminPrincipal()
    {
        authenticate("user1");

        // Create a satellite.
        Satellite satellite = new Satellite();
        satellite.setName("TEST-testCreateSatelliteFailsWithNonAdminPrincipal");
        satellite.setDescription("MY DESCRIPTION");
        satellite.setDeleted(false);
        satelliteService.create(satellite);

        // Shouldn't reach this point.
    }

    // Test creation of a satellite fails if the satellite is already marked as deleted on creation.
    @Test(expected = AccessDeniedException.class)
    public void testCreateSatelliteFailsWithDeletedSatellite()
    {
        authenticate("admin");

        // Create a satellite.
        Satellite satellite = new Satellite();
        satellite.setName("TEST-testCreateSatelliteFailsWithDeletedSatellite");
        satellite.setDescription("MY DESCRIPTION");
        satellite.setDeleted(true);
        satelliteService.create(satellite);

        // Shouldn't reach this point.
    }

    // Test successful update of a satellite.
    @Test
    public void testUpdateSatellite()
    {
        authenticate("admin");

        // Get a satellite.
        Satellite satellite = satelliteRepository.findByName("METEOSAT");
        Assert.isTrue(satellite != null);

        // Change something.
        satellite.setName("TEST-testUpdateSatellite");

        // Update this satellite.
        satelliteService.update(satellite);

        // Flush changes.
        flushTransaction();

        // Check that it's really updated.
        Satellite satellite2 = satelliteRepository.findByName("TEST-testUpdateSatellite");
        Assert.isTrue(satellite2 != null);
        Assert.isTrue(satellite != satellite2);
        Assert.isTrue(satellite.equals(satellite2));
    }

    // Test update of a satellite fails if principal is not an administrator.
    @Test(expected = AccessDeniedException.class)
    public void testUpdateSatelliteFailsIfNotAdmin()
    {
        authenticate("user1");

        // Get a satellite.
        Satellite satellite = satelliteRepository.findByName("METEOSAT");
        Assert.isTrue(satellite != null);

        // Change something.
        satellite.setName("TEST-testUpdateSatelliteFailsIfNotAdmin");

        // Update this satellite.
        satelliteService.update(satellite);

        // Shouldn't reach this point.
    }

    // Test update of a satellite fails if an attempt to add a deleted platform is made.
    @Test(expected = AccessDeniedException.class)
    public void testUpdateSatelliteFailsWithDeletedPlatform()
    {
        authenticate("admin");

        // Get a satellite.
        Satellite satellite = satelliteRepository.findByName("METEOSAT");
        Assert.isTrue(satellite != null);

        // Get a deleted platform.
        Platform platform = platformRepository.findByName("DELETED-PLATFORM");
        Assert.isTrue(platform != null);

        // Add it to the satellite.
        satellite.getPlatforms().add(platform);

        // Update this satellite.
        satelliteService.update(satellite);

        // Shouldn't reach this point.
    }

    // Test update of a satellite fails if satellite was deleted.
    @Test(expected = AccessDeniedException.class)
    public void testUpdateSatelliteFailsWithDeletedSatellite()
    {
        authenticate("admin");

        // Get a satellite.
        Satellite satellite = satelliteRepository.findByName("DELETED-SAT");
        Assert.isTrue(satellite != null);

        // Change something.
        satellite.setName("asdfasdf");

        // Update this satellite.
        satelliteService.update(satellite);

        // Shouldn't reach this point.
    }

    // Test update of a satellite fails if an attempt to set the deleted flag is made.
    // The deleted flag should only be set by using the delete method.
    @Test(expected = AccessDeniedException.class)
    public void testUpdateSatelliteFailsToDeleteSatellite()
    {
        authenticate("admin");

        // Get a satellite.
        Satellite satellite = satelliteRepository.findByName("METEOSAT");
        Assert.isTrue(satellite != null);

        // Set the deleted flag.
        satellite.setDeleted(true);

        // Update this satellite.
        satelliteService.update(satellite);

        // Shouldn't reach this point.
    }

    // Test successful deletion of a satellite.
    @Test
    public void testDeleteSatellite()
    {
        authenticate("admin");

        // Get a satellite.
        Satellite satellite = satelliteRepository.findByName("METEOSAT");
        Assert.isTrue(satellite != null);

        // It is not deleted yet.
        Assert.isTrue(!satellite.getDeleted());

        // Delete this satellite.
        satelliteService.delete(satellite.getId());

        // Flush changes.
        flushTransaction();

        // Check that it's really deleted.
        Satellite satellite2 = satelliteRepository.findByName("METEOSAT");
        Assert.isTrue(satellite2 != null);
        Assert.isTrue(satellite != satellite2);
        Assert.isTrue(satellite.equals(satellite2));

        Assert.isTrue(satellite2.getDeleted());

        // Check that it has no platforms associated.
        Assert.isTrue(satellite2.getPlatforms().isEmpty());

    }

    // Test deletion of a satellite fails if not admin.
    @Test(expected = AccessDeniedException.class)
    public void testDeleteSatelliteFailsIfNotAdmin()
    {
        authenticate("user1");

        // Get a satellite.
        Satellite satellite = satelliteRepository.findByName("METEOSAT");
        Assert.isTrue(satellite != null);

        // It is not deleted yet.
        Assert.isTrue(!satellite.getDeleted());

        // Delete this satellite.
        satelliteService.delete(satellite.getId());

        // Shouldn't reach this point.
    }


    // Test deletion of a satellite fails if already deleted.
    @Test(expected = AccessDeniedException.class)
    public void testDeleteSatelliteFailsIfAlreadyDeleted()
    {
        authenticate("admin");

        // Get a satellite.
        Satellite satellite = satelliteRepository.findByName("DELETED-SAT");
        Assert.isTrue(satellite != null);

        // It is deleted already.
        Assert.isTrue(satellite.getDeleted());

        // Delete this satellite.
        satelliteService.delete(satellite.getId());

        // Shouldn't reach this point.
    }

    // Test listing of satellites.
    public void testSatelliteIndex()
    {
        // No authentication required for this.
        unauthenticate();

        // Get listing.
        List<Satellite> satellites = satelliteService.findAllForIndex();

        // All satellites should appear.
        Satellite satelliteThatShouldAppear = satelliteRepository.findByName("METEOSAT");

        // Unless they are deleted.
        Satellite satelliteThatShouldntAppear = satelliteRepository.findByName("DELETED-SAT");

        Assert.isTrue(satellites.contains(satelliteThatShouldAppear));
        Assert.isTrue(!satellites.contains(satelliteThatShouldntAppear));
    }

    // Test search of satellites.
    public void testSatelliteSearch()
    {
        // No authentication required for this.
        unauthenticate();

        // Search for "sat".
        List<Satellite> satellites = satelliteService.search("america");

        // All satellites shown should contain the word network in the name..
        for (Satellite satellite : satellites) {
            Assert.isTrue(satellite.getName().toLowerCase().contains("america"));
        }

        // Search for "deleted".
        satellites = satelliteService.search("deleted");

        // The deleted satellite should not appear as it is deleted.
        Assert.isTrue(satellites.isEmpty());

        // A common mistake with Lucene is not handling the exception that occurs
        // when only a extremely common word is used, such as "this".
        //
        // This happens because Lucene does not index very common words and removes them from the query,
        // if the query is empty as a result an exception is thrown.
        //
        // Ensure nothing is thrown when a search for "this" is made.
        satellites = satelliteService.search("this");

        // In that case, the full listing should be shown.
        Assert.isTrue(satellites.size() == satelliteService.findAllForIndex().size());
    }
}
