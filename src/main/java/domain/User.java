package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class User
        extends Actor
{
    private List<Antenna> antennas = new ArrayList<>();
    private List<Tutorial> tutorials = new ArrayList<>();
    private List<PlatformSubscription> platformSubscriptions = new ArrayList<>();

    @Valid
    @OneToMany(mappedBy = "user")
    public List<Antenna> getAntennas()
    {
        return antennas;
    }

    public void setAntennas(List<Antenna> antennas)
    {
        if (antennas == null) antennas = new ArrayList<>();
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
        if (tutorials == null) tutorials = new ArrayList<>();
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
        if (platformSubscriptions == null) platformSubscriptions = new ArrayList<>();
        this.platformSubscriptions = platformSubscriptions;
    }
}