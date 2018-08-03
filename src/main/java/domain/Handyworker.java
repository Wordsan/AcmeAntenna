
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Handyworker extends Actor {

	private Collection<String>				antennaModels;
	private Collection<MaintenanceRequest>	requests;


	public Handyworker() {
		super();
	}

	@ElementCollection
	public Collection<String> getAntennaModels() {
		return this.antennaModels;
	}

	public void setAntennaModels(final Collection<String> antennaModels) {
		this.antennaModels = antennaModels;
	}

	@OneToMany(mappedBy = "handyworker")
	public Collection<MaintenanceRequest> getRequests() {
		return this.requests;
	}

	public void setRequests(final Collection<MaintenanceRequest> requests) {
		this.requests = requests;
	}

}
