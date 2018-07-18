package services;

import org.apache.lucene.search.Query;
import org.hibernate.search.errors.EmptyQueryException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import domain.Antenna;
import domain.Platform;
import domain.User;
import exceptions.ResourceNotFoundException;
import repositories.AntennaRepository;
import repositories.PlatformRepository;
import security.Authority;
import utilities.CheckUtils;

@Service
@Transactional
public class PlatformService {
    @Autowired PlatformRepository repository;
    @Autowired UserService userService;

    @PersistenceContext EntityManager entityManager; // For Lucene.

    public List<Platform> findAll()
    {
        return repository.findAllByOrderByNameAsc();
    }

    public List<Platform> search(String searchTerms)
    {
        if (searchTerms != null && !searchTerms.trim().isEmpty()) {
            try {
                FullTextEntityManager ftem = Search.getFullTextEntityManager(entityManager);
                QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(Platform.class).get();
                Query query = qb.keyword().onFields("name", "description").matching(searchTerms).createQuery();

                //noinspection unchecked
                return (List<Platform>) ftem.createFullTextQuery(query, Platform.class).getResultList();
            } catch (EmptyQueryException ex) {
                // This happens if you search by a very common word that Lucene rejects for being too common.
                // If it's the only word in the query the query becomes empty and that's not allowed.
                // It's okay, just fall through to returning everything.
            }
        }
        return findAll();
    }
}