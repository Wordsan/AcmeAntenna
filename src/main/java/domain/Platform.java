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
@Indexed(interceptor = Platform.PlatformIndexingInterceptor.class)
@Access(AccessType.PROPERTY)
public class Platform
extends DomainEntity
{
    private String name;
    private String description;
    private List<Satellite> satellites = new ArrayList<>();
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
    @ManyToMany(mappedBy = "platforms")
    public List<Satellite> getSatellites()
    {
        return satellites;
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

    public void setSatellites(List<Satellite> satellites)
    {
        if (satellites == null) satellites = new ArrayList<>();
        this.satellites = satellites;
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    // This alters the behavior for Lucene.
    // Soft-deleted entities are not to be indexed.
    public static class PlatformIndexingInterceptor implements EntityIndexingInterceptor<Platform>
    {
        @Override
        public IndexingOverride onAdd(Platform entity)
        {
            if (entity.getDeleted()) {
                return IndexingOverride.SKIP;
            }
            return IndexingOverride.APPLY_DEFAULT;
        }

        @Override
        public IndexingOverride onUpdate(Platform entity)
        {
            if (entity.getDeleted()) {
                return IndexingOverride.REMOVE;
            }
            return IndexingOverride.APPLY_DEFAULT;
        }

        @Override
        public IndexingOverride onDelete(Platform entity)
        {
            return IndexingOverride.APPLY_DEFAULT;
        }

        @Override
        public IndexingOverride onCollectionUpdate(Platform entity)
        {
            if (entity.getDeleted()) {
                return IndexingOverride.REMOVE;
            }
            return IndexingOverride.APPLY_DEFAULT;
        }
    }

}
