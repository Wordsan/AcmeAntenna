package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Satellite
extends DomainEntity {
    private String name = "";
    private String description = "";
    private List<Platform> platforms = new ArrayList<>();

    @NotNull
    @NotBlank
    public String getName()
    {
        return name;
    }

    @NotNull
    @NotBlank
    public String getDescription()
    {
        return description;
    }

    @ManyToMany(mappedBy = "satellites")
    @Valid
    @NotEmpty
    public List<Platform> getPlatforms()
    {
        return platforms;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setPlatforms(List<Platform> platforms)
    {
        this.platforms = platforms;
    }
}
