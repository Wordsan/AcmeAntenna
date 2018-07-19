package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ResponseCache;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import domain.Antenna;
import domain.Tutorial;
import domain.User;
import exceptions.ResourceNotFoundException;
import repositories.AntennaRepository;
import repositories.TutorialRepository;
import security.Authority;
import utilities.CheckUtils;

@Service
@Transactional
public class TutorialService {
	@Autowired private TutorialRepository repository;

	public List<Tutorial> findAll()
	{
		return repository.findAll();
	}

	public Tutorial getById(int id)
	{
		Tutorial result = repository.findOne(id);
		if (result == null) throw new ResourceNotFoundException();
		return result;
	}

	public void delete(int id)
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		repository.delete(id);
	}
}