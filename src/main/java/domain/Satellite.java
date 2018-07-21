package domain;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Indexed
public class Satellite
extends DomainEntity {
    private String name = "";
    private String description = "";
    private List<Platform> platforms = new ArrayList<>();

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
    @ManyToMany(mappedBy = "satellites")
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
