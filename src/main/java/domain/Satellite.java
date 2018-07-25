package domain;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.indexes.interceptor.EntityIndexingInterceptor;
import org.hibernate.search.indexes.interceptor.IndexingOverride;
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
@Indexed(interceptor = Satellite.SatelliteIndexingInterceptor.class)
public class Satellite
extends DomainEntity {
    private String name;
    private String description;
    private List<Platform> platforms = new ArrayList<>();
    private boolean deleted;

    @NotBlank
    @Field
    public String getName()
    {
        return name;
    }

    @NotBlank
    @Field
    @Lob
    public String getDescription()
    {
        return description;
    }

    @Valid
    @ManyToMany
    public List<Platform> getPlatforms()
    {
        return platforms;
    }

    public boolean getDeleted()
    {
        return deleted;
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
        if (platforms == null) platforms = new ArrayList<>();
        this.platforms = platforms;
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    // This alters the behavior for Lucene.
    // Soft-deleted entities are not to be indexed.
    public static class SatelliteIndexingInterceptor implements EntityIndexingInterceptor<Satellite>
    {
        @Override
        public IndexingOverride onAdd(Satellite entity)
        {
            if (entity.getDeleted()) {
                return IndexingOverride.SKIP;
            }
            return IndexingOverride.APPLY_DEFAULT;
        }

        @Override
        public IndexingOverride onUpdate(Satellite entity)
        {
            if (entity.getDeleted()) {
                return IndexingOverride.REMOVE;
            }
            return IndexingOverride.APPLY_DEFAULT;
        }

        @Override
        public IndexingOverride onDelete(Satellite entity)
        {
            return IndexingOverride.APPLY_DEFAULT;
        }

        @Override
        public IndexingOverride onCollectionUpdate(Satellite entity)
        {
            if (entity.getDeleted()) {
                return IndexingOverride.REMOVE;
            }
            return IndexingOverride.APPLY_DEFAULT;
        }
    }
}
