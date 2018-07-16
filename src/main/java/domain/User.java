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
}