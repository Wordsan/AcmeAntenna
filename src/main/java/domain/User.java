
package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	private List<Antenna>					antennas				= new ArrayList<>();
	private List<Tutorial>					tutorials				= new ArrayList<>();
	private List<PlatformSubscription>		platformSubscriptions	= new ArrayList<>();
	private Collection<MaintenanceRequest>	requests				= new ArrayList<>();
	private String							creditCardCookie;


	@Valid
	@OneToMany(mappedBy = "user")
	public List<Antenna> getAntennas() {
		return this.antennas;
	}
	public void setAntennas(List<Antenna> antennas) {
		if (antennas == null)
			antennas = new ArrayList<>();
		this.antennas = antennas;
	}

	@Valid
	@OneToMany(mappedBy = "user")
	public List<Tutorial> getTutorials() {
		return this.tutorials;
	}
	public void setTutorials(List<Tutorial> tutorials) {
		if (tutorials == null)
			tutorials = new ArrayList<>();
		this.tutorials = tutorials;
	}

	@Valid
	@OneToMany(mappedBy = "user")
	public List<PlatformSubscription> getPlatformSubscriptions() {
		return this.platformSubscriptions;
	}
	public void setPlatformSubscriptions(List<PlatformSubscription> platformSubscriptions) {
		if (platformSubscriptions == null)
			platformSubscriptions = new ArrayList<>();
		this.platformSubscriptions = platformSubscriptions;
	}

	@OneToMany(mappedBy = "user")
	public Collection<MaintenanceRequest> getRequests() {
		return this.requests;
	}
	public void setRequests(final Collection<MaintenanceRequest> requests) {
		this.requests = requests;
	}

	public String getCreditCardCookie() {
		return this.creditCardCookie;
	}

	public void setCreditCardCookie(final String creditCardCookie) {
		this.creditCardCookie = creditCardCookie;
	}
}
