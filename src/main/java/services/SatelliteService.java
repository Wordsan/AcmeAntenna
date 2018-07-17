package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

import domain.Satellite;
import repositories.SatelliteRepository;

@Service
@Transactional
public class SatelliteService {
	@Autowired SatelliteRepository repository;

	public List<Satellite> findAllSortedByName()
	{
		return repository.findAllByOrderByNameAsc();
	}
}