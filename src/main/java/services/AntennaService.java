package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import javax.transaction.Transactional;

import domain.Antenna;
import domain.User;
import exceptions.ResourceNotFoundException;
import repositories.AntennaRepository;
import security.Authority;
import utilities.CheckUtils;

@Service
@Transactional
public class AntennaService {
	@Autowired AntennaRepository repository;
	@Autowired UserService userService;

	public Antenna create(Antenna submittedAntenna)
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);
		CheckUtils.checkIsPrincipal(submittedAntenna.getUser());
		CheckUtils.checkNotExists(submittedAntenna);

		return repository.save(submittedAntenna);
	}

	public Antenna update(Antenna submittedAntenna)
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);
		CheckUtils.checkIsPrincipal(submittedAntenna.getUser());
		CheckUtils.checkExists(submittedAntenna);

		Antenna oldAntenna = repository.findOne(submittedAntenna.getId());
		CheckUtils.checkIsPrincipal(oldAntenna.getUser());

		return repository.save(submittedAntenna);
	}

	public Antenna getByIdForEdit(int id)
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);
		Antenna antenna = repository.findOne(id);
		if (antenna == null) throw new ResourceNotFoundException();
		CheckUtils.checkIsPrincipal(antenna.getUser());
		return antenna;
	}

	public void delete(int id)
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);
		Antenna antenna = repository.findOne(id);
		CheckUtils.checkIsPrincipal(antenna.getUser());
		repository.delete(antenna);
	}

	public List<Antenna> findAllForUser()
	{
		CheckUtils.checkPrincipalAuthority(Authority.USER);
		User user = userService.getPrincipal();
		return repository.findAllByUserOrderBySerialNumberAsc(user);
	}
}