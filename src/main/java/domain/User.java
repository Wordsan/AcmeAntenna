package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class User
extends Actor {
	private List<Antenna> antennas;
    private List<Tutorial> tutorials;
    private List<PlatformSubscription> platformSubscriptions;

    @Valid
    @OneToMany(mappedBy = "user")
    public List<Antenna> getAntennas()
    {
        return antennas;
    }
    public void setAntennas(List<Antenna> antennas)
    {
        this.antennas = antennas;
    }

    @Valid
    @OneToMany(mappedBy = "user")
    public List<Tutorial> getTutorials()
    {
        return tutorials;
    }
    public void setTutorials(List<Tutorial> tutorials)
    {
        this.tutorials = tutorials;
    }

    @Valid
    @OneToMany(mappedBy = "user")
    public List<PlatformSubscription> getPlatformSubscriptions()
    {
        return platformSubscriptions;
    }
    public void setPlatformSubscriptions(List<PlatformSubscription> platformSubscriptions)
    {
        this.platformSubscriptions = platformSubscriptions;
    }
}