package services;

import org.apache.lucene.search.Query;
import org.hibernate.search.errors.EmptyQueryException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import domain.Platform;
import domain.Satellite;
import exceptions.ResourceNotFoundException;
import repositories.SatelliteRepository;
import security.Authority;
import utilities.CheckUtils;

@Service
@Transactional
public class SatelliteService {
	@Autowired private SatelliteRepository repository;
	@PersistenceContext private EntityManager entityManager; // For Lucene.

	public List<Satellite> findAllForIndex()
	{
		return repository.findAllForIndex();
	}

	@SuppressWarnings("unchecked")
	public List<Satellite> search(String searchTerms)
	{
		if (searchTerms != null && !searchTerms.trim().isEmpty()) {
			try {
				FullTextEntityManager ftem = Search.getFullTextEntityManager(entityManager);
				QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(Satellite.class).get();
				Query query = qb.keyword().onFields("name", "description").matching(searchTerms).createQuery();

				return (List<Satellite>) ftem.createFullTextQuery(query, Satellite.class).getResultList();
			} catch (EmptyQueryException ex) {
				// This happens if you search by a very common word that Lucene rejects for being too common.
				// If it's the only word in the query the query becomes empty and that's not allowed.
				// It's okay, just fall through to returning everything.
			}
		}
		return findAllForIndex();
	}

	public Satellite getById(int id)
	{
		Satellite result = repository.findOne(id);
		if (result == null) throw new ResourceNotFoundException();
		return result;
	}

	public Satellite getByIdForEdit(int id)
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return getById(id);
	}

	public Satellite create(Satellite satellite)
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		CheckUtils.checkNotExists(satellite);
		CheckUtils.checkFalse(satellite.getDeleted());

		for (Platform platform : satellite.getPlatforms()) {
			CheckUtils.checkFalse(platform.getDeleted());
		}

		return repository.save(satellite);
	}
	public Satellite update(Satellite satellite)
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		CheckUtils.checkExists(satellite);
		CheckUtils.checkFalse(satellite.getDeleted());

		for (Platform platform : satellite.getPlatforms()) {
			CheckUtils.checkFalse(platform.getDeleted());
		}

		return repository.save(satellite);
	}

	public void delete(int id)
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);

		Satellite satellite = repository.findOne(id);
		CheckUtils.checkFalse(satellite.getDeleted());

		satellite.setDeleted(true);

		// We are the owner side of this relationship.
		satellite.getPlatforms().clear();

		repository.save(satellite);
	}

	// For use from PlatformService only.
	void updatePlatformRelation(Platform platform, List<Satellite> previousSatellites)
	{
		Set<Satellite> before = new HashSet<>(previousSatellites);
		Set<Satellite> after = new HashSet<>(platform.getSatellites());

		// Deleted platforms should be empty!
		CheckUtils.checkTrue(after.isEmpty() || !platform.getDeleted());

		// Deleted satellites cannot be associated with platforms!
		for (Satellite satellite : after) {
			CheckUtils.checkFalse(satellite.getDeleted());
		}

		Set<Satellite> added = new HashSet<>();
		added.addAll(after);
		added.removeAll(before);

		Set<Satellite> removed = new HashSet<>();
		removed.addAll(before);
		removed.removeAll(after);

		for (Satellite satellite : added) {
			if (!satellite.getPlatforms().contains(platform)) satellite.getPlatforms().add(platform);
			repository.save(satellite);
		}

		for (Satellite satellite : removed) {
			satellite.getPlatforms().remove(platform);
			repository.save(satellite);
		}
	}
}