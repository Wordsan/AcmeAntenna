package services;

import org.apache.lucene.search.Query;
import org.hibernate.search.errors.EmptyQueryException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import domain.Platform;
import domain.Satellite;
import exceptions.ResourceNotFoundException;
import repositories.PlatformRepository;
import security.Authority;
import utilities.CheckUtils;

@Service
@Transactional
public class PlatformService {
    @Autowired private PlatformRepository repository;
    @Autowired private SatelliteService satelliteService;
    @PersistenceContext private EntityManager entityManager; // For Lucene.

    public List<Platform> findAllForIndex()
    {
        return repository.findAllForIndex();
    }

    @SuppressWarnings("unchecked")
    public List<Platform> search(String searchTerms)
    {
        if (searchTerms != null && !searchTerms.trim().isEmpty()) {
            try {
                FullTextEntityManager ftem = Search.getFullTextEntityManager(entityManager);
                QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(Platform.class).get();
                Query query = qb.keyword().onFields("name", "description").matching(searchTerms).createQuery();

                return (List<Platform>) ftem.createFullTextQuery(query, Platform.class).getResultList();
            } catch (EmptyQueryException ex) {
                // This happens if you search by a very common word that Lucene rejects for being too common.
                // If it's the only word in the query the query becomes empty and that's not allowed.
                // It's okay, just fall through to returning everything.
            }
        }

        return findAllForIndex();
    }

    public Platform getById(int id)
    {
        Platform result = repository.findOne(id);
        if (result == null) throw new ResourceNotFoundException();
        return result;
    }

    public Platform create(Platform platform)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        CheckUtils.checkNotExists(platform);

        return repository.save(platform);
    }
    public Platform update(Platform platform)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        CheckUtils.checkFalse(platform.getDeleted());
        CheckUtils.checkExists(platform);

        return repository.save(platform);
    }

    public void delete(int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

        Platform platform = repository.findOne(id);
        CheckUtils.checkFalse(platform.getDeleted());

        platform.setDeleted(true);
        for (Satellite satellite : platform.getSatellites()) {
            satelliteService.unlinkPlatform(satellite, platform);
        }
        repository.save(platform);
    }

    public Platform getByIdForEdit(int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        return getById(id);
    }
}