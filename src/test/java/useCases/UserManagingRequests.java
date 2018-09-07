
package useCases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.AntennaService;
import services.HandyworkerService;
import services.MaintenanceRequestService;
import services.UserService;
import utilities.AbstractTest;
import domain.Antenna;
import domain.Handyworker;
import domain.MaintenanceRequest;

/**
 * Tests the following use cases from the requirements document:
 * - Register to the system as a user.
 */
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserManagingRequests extends AbstractTest {

	@Autowired
	private UserService					userService;

	@Autowired
	private MaintenanceRequestService	maintenanceRequestService;
	@Autowired
	private HandyworkerService			handyworkerService;
	@Autowired
	private AntennaService				antennaService;


	//test getting the requests already serviced from an user
	@Test
	public void testYetServicedRequest() {
		this.unauthenticate();
		//Auth as an user
		this.authenticate("user1");

		//Obtain already serviced requests
		final Collection<MaintenanceRequest> requests = this.userService.findServedMainteinanceRequest(this.userService.findPrincipal());
		//Cannot be empty because it has one request declared at Populatedatabase.xml
		Assert.notEmpty(requests);

	}
	//test getting the requests not already serviced from an user
	@Test
	public void testYetNotServicedRequest() {
		this.unauthenticate();
		//Auth as an user
		this.authenticate("user1");

		//Obtain not already serviced requests
		final Collection<MaintenanceRequest> requests = this.userService.findNotServedMainteinanceRequest(this.userService.findPrincipal());
		//Cannot be empty because it has one request declared at Populatedatabase.xml
		Assert.notEmpty(requests);

	}
	//test getting the requests without being authenticated
	@Test(expected = IllegalArgumentException.class)
	public void testYetServicedRequestsUnauthenticated() throws IllegalArgumentException {
		this.unauthenticate();
		//Trying to obtain the requests without being authenticated
		final Collection<MaintenanceRequest> requests = this.userService.findNotServedMainteinanceRequest(null);
	}

	//test getting the requests not serviced unauthenticated 
	@Test(expected = IllegalArgumentException.class)
	public void testNotYetServicedRequestsUnauthenticated() throws IllegalArgumentException {
		this.unauthenticate();

		//Trying to obtain the requests without being authenticated
		final Collection<MaintenanceRequest> requests = this.userService.findNotServedMainteinanceRequest(null);
	}
	//Make a request for maintenance to a handyworker.
	@Test
	public void testCreateARequest() {
		this.unauthenticate();
		//auth as an user
		this.authenticate("user1");

		//Creation of the request
		final MaintenanceRequest request = this.maintenanceRequestService.create();
		request.setUser(this.userService.findPrincipal());
		request.setDescription("This is a description");
		//Declaration of handyworkers and antennas, needed for the creation of the request
		final List<Handyworker> workers = new ArrayList<Handyworker>(this.handyworkerService.findAll());
		final List<Antenna> antennas = new ArrayList<Antenna>(this.antennaService.findAll());
		request.setHandyworker(workers.get(0));
		request.setAntenna(antennas.get(0));

		final MaintenanceRequest res = this.maintenanceRequestService.save(request);
		//Is the user assigned to the request the same who is authenticated?
		Assert.isTrue(res.getUser().equals(this.userService.findPrincipal()));
		//Is the request on the user's list of non served requests?
		Assert.isTrue(this.userService.findNotServedMainteinanceRequest(this.userService.findPrincipal()).contains(res));

	}
	//Make a request for maintenance to a handyworker.
	//Making the request without being authenticated
	@Test(expected = IllegalArgumentException.class)
	public void testCreateRequestUnauthenticated() throws IllegalArgumentException {
		this.unauthenticate();

		final MaintenanceRequest request = this.maintenanceRequestService.create();
		request.setDescription("This is a description");
		//Declaration of handyworkers and antennas, needed for the creation of the request
		final List<Handyworker> workers = new ArrayList<Handyworker>(this.handyworkerService.findAll());
		final List<Antenna> antennas = new ArrayList<Antenna>(this.antennaService.findAll());
		request.setHandyworker(workers.get(0));
		request.setAntenna(antennas.get(0));

		final MaintenanceRequest res = this.maintenanceRequestService.save(request);

	}
	//Make a request for maintenance to a handyworker.
	//Making the request providing no description
	@Test(expected = IllegalArgumentException.class)
	public void testCreateRequestWithoutDescription() throws IllegalArgumentException {
		this.unauthenticate();
		this.authenticate("user1");
		final MaintenanceRequest request = this.maintenanceRequestService.create();
		request.setUser(this.userService.findPrincipal());

		//Declaration of handyworkers and antennas, needed for the creation of the request
		final List<Handyworker> workers = new ArrayList<Handyworker>(this.handyworkerService.findAll());
		final List<Antenna> antennas = new ArrayList<Antenna>(this.antennaService.findAll());
		request.setHandyworker(workers.get(0));
		request.setAntenna(antennas.get(0));

		final MaintenanceRequest res = this.maintenanceRequestService.save(request);

	}
	//Make a request for maintenance to a handyworker.
	//Making the request without any antenna selected
	@Test(expected = IllegalArgumentException.class)
	public void testCreateRequestWithoutAntenna() throws IllegalArgumentException {
		this.unauthenticate();
		this.authenticate("user1");
		final MaintenanceRequest request = this.maintenanceRequestService.create();
		request.setUser(this.userService.findPrincipal());
		request.setDescription("This is a description");
		//Declaration of handyworkers and antennas, needed for the creation of the request
		final List<Handyworker> workers = new ArrayList<Handyworker>(this.handyworkerService.findAll());
		request.setHandyworker(workers.get(0));

		final MaintenanceRequest res = this.maintenanceRequestService.save(request);

	}
	//Make a request for maintenance to a handyworker.
	//Making the request without worker
	@Test(expected = IllegalArgumentException.class)
	public void testCreateRequestWithoutWorker() throws IllegalArgumentException {
		this.unauthenticate();
		this.authenticate("user1");
		final MaintenanceRequest request = this.maintenanceRequestService.create();
		request.setUser(this.userService.findPrincipal());
		request.setDescription("This is a description");
		//Declaration of handyworkers and antennas, needed for the creation of the request
		final List<Antenna> antennas = new ArrayList<Antenna>(this.antennaService.findAll());
		request.setAntenna(antennas.get(0));

		final MaintenanceRequest res = this.maintenanceRequestService.save(request);

	}
	//Make a request for maintenance to a handyworker.
	//Making the request as an admin
	@Test(expected = IllegalArgumentException.class)
	public void testCreateARequestBeingAdmin() throws IllegalArgumentException {
		this.unauthenticate();
		//auth as an user
		this.authenticate("admin");

		//Creation of the request
		final MaintenanceRequest request = this.maintenanceRequestService.create();
		request.setUser(this.userService.findPrincipal());
		request.setDescription("This is a description");
		//Declaration of handyworkers and antennas, needed for the creation of the request
		final List<Handyworker> workers = new ArrayList<Handyworker>(this.handyworkerService.findAll());
		final List<Antenna> antennas = new ArrayList<Antenna>(this.antennaService.findAll());
		request.setHandyworker(workers.get(0));
		request.setAntenna(antennas.get(0));

		final MaintenanceRequest res = this.maintenanceRequestService.save(request);
		//Is the user assigned to the request the same who is authenticated?
		Assert.isTrue(res.getUser().equals(this.userService.findPrincipal()));
		//Is the request on the user's list of non served requests?
		Assert.isTrue(this.userService.findNotServedMainteinanceRequest(this.userService.findPrincipal()).contains(res));

	}

}
