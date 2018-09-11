package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Handyworker extends Actor {

    private Collection<String> antennaModels;
    private Collection<MaintenanceRequest> requests;


    public Handyworker()
    {
        super();
    }

    @Valid
    @ElementCollection
    public Collection<String> getAntennaModels()
    {
        return this.antennaModels;
    }

    public void setAntennaModels(final Collection<String> antennaModels)
    {
        this.antennaModels = antennaModels;
    }

    @NotNull
    @OneToMany(mappedBy = "handyworker")
    public Collection<MaintenanceRequest> getRequests()
    {
        return this.requests;
    }

    public void setRequests(final Collection<MaintenanceRequest> requests)
    {
        this.requests = requests;
    }

}
