package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import domain.Actor;
import domain.Antenna;
import domain.Satellite;
import domain.User;
import repositories.AntennaRepository;
import repositories.SatelliteRepository;
import utilities.AbstractTest;

/**
 * Tests the following use case:
 * - Manage his or her antennas, which includes registering them, editing them, deleting
 * them, and listing them. The information about an antenna is private to the actor
 * who registers it.
 */
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AntennaServiceTest extends AbstractTest {
    @Autowired private AntennaService antennaService;
    @Autowired private AntennaRepository antennaRepository;
    @Autowired private SatelliteRepository satelliteRepository;

    // Test successful creation of an antenna.
    @Test
    public void testCreateAntenna()
    {
        authenticate("user1");

        Antenna antenna = new Antenna();
        antenna.setUser((User) getActor("user1"));
        antenna.setModel("Model X");
        antenna.setSerialNumber("SX1123");
        antenna.setPositionLatitude(10);
        antenna.setPositionLongitude(10);
        antenna.setRotationAzimuth(10);
        antenna.setRotationElevation(10);
        antenna.setSignalQuality(100);
        antenna.setSatellite(satelliteRepository.findByName("METEOSAT"));
        antennaService.create(antenna);
    }

    // Test creation of an antenna fails if principal does not match antenna owner.
    @Test(expected = AccessDeniedException.class)
    public void testCreateAntennaFailsWithPrincipalNotMatchingOwner()
    {
        authenticate("user1");

        Antenna antenna = new Antenna();
        antenna.setUser((User) getActor("user2")); // not user1
        antenna.setModel("Model X");
        antenna.setSerialNumber("SX1123");
        antenna.setPositionLatitude(10);
        antenna.setPositionLongitude(10);
        antenna.setRotationAzimuth(10);
        antenna.setRotationElevation(10);
        antenna.setSignalQuality(100);
        antenna.setSatellite(satelliteRepository.findByName("METEOSAT"));
        antennaService.create(antenna);
    }

    // Test creation of an antenna fails if satellite is deleted.
    @Test(expected = AccessDeniedException.class)
    public void testCreateAntennaFailsWithDeletedSatellite()
    {
        authenticate("user1");

        Antenna antenna = new Antenna();
        antenna.setUser((User) getActor("user1"));
        antenna.setModel("Model X");
        antenna.setSerialNumber("SX1123");
        antenna.setPositionLatitude(10);
        antenna.setPositionLongitude(10);
        antenna.setRotationAzimuth(10);
        antenna.setRotationElevation(10);
        antenna.setSignalQuality(100);
        antenna.setSatellite(satelliteRepository.findByName("DELETED-SAT"));
        antennaService.create(antenna);
    }

    // Test creation of an antenna fails if passed an already existing antenna.
    @Test(expected = AccessDeniedException.class)
    public void testCreateAntennaFailsWithExistingAntenna()
    {
        authenticate("user1");

        Antenna antenna = antennaRepository.findBySerialNumber("A001");
        antennaService.create(antenna);
    }

    // Test successful update of an antenna.
    @Test
    public void testUpdateAntenna()
    {
        authenticate("user1");

        // Get an antenna belonging to this principal.
        Antenna antenna = antennaRepository.findBySerialNumber("A001");

        // Change anything.
        antenna.setSerialNumber("TEST-testUpdateAntenna");

        // See if it updates correctly.
        antenna = antennaService.update(antenna);

        // Flush changes and detach all entities.
        flushTransaction();

        // Reload entity.
        antenna = antennaRepository.findOne(antenna.getId());

        // Check that it's really updated.
        Assert.isTrue(antenna.getSerialNumber().equals("TEST-testUpdateAntenna"));
    }

    // Test an antenna can be updated, even if it's satellite is deleted, as long as the satellite has not been changed.
    @Test
    public void testUpdateAntennaWithDeletedSat()
    {
        authenticate("user1");

        // Get an antenna belonging to this principal with a deleted satellite.
        Antenna antenna = antennaRepository.findBySerialNumber("D001");
        Satellite deletedSat = satelliteRepository.findByName("DELETED-SAT");

        Assert.isTrue(antenna.getSatellite().equals(deletedSat));

        // Change anything.
        antenna.setSerialNumber("TEST-testUpdateAntennaWithDeletedSat");

        // See if it updates correctly.
        antenna = antennaService.update(antenna);

        // Flush changes and detach all entities.
        flushTransaction();

        // Reload entity.
        antenna = antennaRepository.findOne(antenna.getId());

        // Check that it's really updated.
        Assert.isTrue(antenna.getSerialNumber().equals("TEST-testUpdateAntennaWithDeletedSat"));
    }

    // Test an antenna cannot be updated to point to a deleted satellite if it wasn't pointing to it before.
    @Test(expected = AccessDeniedException.class)
    public void testUpdateAntennaFailsToPointToDeletedSat()
    {
        authenticate("user1");

        // Get an antenna belonging to this principal without a deleted satellite.
        Antenna antenna = antennaRepository.findBySerialNumber("A001");
        Satellite deletedSat = satelliteRepository.findByName("DELETED-SAT");

        Assert.isTrue(!antenna.getSatellite().equals(deletedSat));

        // Point to deleted satellite.
        antenna.setSatellite(deletedSat);

        // See if it updates correctly.
        antennaService.update(antenna);
    }

    // Test update of an antenna fails if principal does not match antenna owner.
    @Test(expected = AccessDeniedException.class)
    public void testUpdateAntennaFailsWithPrincipalNotMatchingOwner()
    {
        authenticate("user1");

        Actor user2 = getActor("user2");

        // Get any antenna belonging to this principal.
        Antenna antenna = ((User) user2).getAntennas().get(0);

        // Change anything.
        antenna.setModel("TEST-testUpdateAntennaFailsWithPrincipalNotMatchingOwner");

        // See if it fails correctly.
        antennaService.update(antenna);
    }

    // Test update of an antenna fails if an attempt to change the owner is made.
    @Test(expected = AccessDeniedException.class)
    public void testUpdateAntennaFailsOnOwnerChangeAttempt()
    {
        authenticate("user1");

        Actor principal = getPrincipal();
        Actor user2 = getActor("user2");

        // Get any antenna belonging to this principal.
        Antenna antenna = ((User) principal).getAntennas().get(0);

        // Change the owner to user2.
        antenna.setUser((User) user2);

        // See if it fails correctly.
        antennaService.update(antenna);
    }

    // Test update method rejects new antennas.
    @Test(expected = AccessDeniedException.class)
    public void testUpdateAntennaFailsIfAntennaIsNew()
    {
        authenticate("user1");

        Antenna antenna = new Antenna();
        antenna.setUser((User) getPrincipal());
        antenna.setModel("Model X");
        antenna.setSerialNumber("SX1123");
        antenna.setPositionLatitude(10);
        antenna.setPositionLongitude(10);
        antenna.setRotationAzimuth(10);
        antenna.setRotationElevation(10);
        antenna.setSignalQuality(100);
        antenna.setSatellite(satelliteRepository.findByName("METEOSAT"));

        // Attempt to save new antenna as if we were updating it.
        antennaService.update(antenna);
    }

    // Test no user can visualize another user's antenna for editing.
    @Test(expected = AccessDeniedException.class)
    public void testGetAntennaForEditFailsIfOwnerNotPrinciapl()
    {
        authenticate("user2");

        int id = ((User) getActor("user1")).getAntennas().get(0).getId();

        // As user2, attempt to visualize user1's antenna.
        antennaService.getByIdForEdit(id);
    }

    // Test successful update of an antenna.
    @Test
    public void testDeleteAntenna()
    {
        authenticate("user1");

        Actor principal = getPrincipal();

        // Get any antenna belonging to this principal.
        Antenna antenna = ((User) principal).getAntennas().get(0);

        // Delete it.
        int antennaId = antenna.getId();
        antennaService.delete(antennaId);

        // Flush changes.
        flushTransaction();

        // Ensure it really doesn't exist.
        Assert.isTrue(antennaRepository.findOne(antennaId) == null);
    }

    // Test deletion of an antenna fails if principal does not match antenna owner.
    @Test(expected = AccessDeniedException.class)
    public void testDeleteAntennaFailsWithPrincipalNotMatchingOwner()
    {
        authenticate("user1");

        Actor user2 = getActor("user2");

        // Get any antenna belonging to user2.
        Antenna antenna = ((User) user2).getAntennas().get(0);

        // Attempt to delete it.
        antennaService.delete(antenna.getId());
    }

    // Test only users can list their antennas.
    @Test(expected = AccessDeniedException.class)
    public void testListAntennaFailsIfNotUser()
    {
        authenticate("admin");
        antennaService.findAllForPrincipal();
    }
}
