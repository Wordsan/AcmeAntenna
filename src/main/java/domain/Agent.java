package domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Agent extends Actor {

    private Collection<Banner> banners = new ArrayList<Banner>();


    public Agent()
    {
        super();
    }

    @NotNull
    @OneToMany(mappedBy = "agent")
    public Collection<Banner> getBanners()
    {
        return this.banners;
    }

    public void setBanners(final Collection<Banner> banners)
    {
        this.banners = banners;
    }

}
