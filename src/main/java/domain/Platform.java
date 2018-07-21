package domain;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Indexed
@Access(AccessType.PROPERTY)
public class Platform
extends DomainEntity
{
    private String name;
    private String description;
    private List<Satellite> satellites;

    @NotBlank
    @NotNull
    @Field
    public String getName()
    {
        return name;
    }

    @NotBlank
    @NotNull
    @Field
    @Lob
    public String getDescription()
    {
        return description;
    }

    @Valid
    @ManyToMany
    public List<Satellite> getSatellites()
    {
        return satellites;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setSatellites(List<Satellite> satellites)
    {
        this.satellites = satellites;
    }
}
