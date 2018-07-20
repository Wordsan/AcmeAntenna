package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ResponseCache;
import java.util.Date;
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

	public Tutorial create(Tutorial tutorial)
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);
		CheckUtils.checkIsPrincipal(tutorial.getUser());
		CheckUtils.checkNotExists(tutorial);

		tutorial.setLastUpdateTime(new Date());
		return repository.save(tutorial);
	}

	public Tutorial update(Tutorial tutorial)
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);
		CheckUtils.checkIsPrincipal(tutorial.getUser());
		CheckUtils.checkExists(tutorial);

		tutorial.setLastUpdateTime(new Date());
		return repository.save(tutorial);
	}

	public Tutorial getByIdForEdit(int id)
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);
		Tutorial tutorial = getById(id);
		CheckUtils.checkIsPrincipal(tutorial.getUser());
		return tutorial;
	}
}