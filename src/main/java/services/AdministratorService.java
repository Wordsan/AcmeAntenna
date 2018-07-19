package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import javax.transaction.Transactional;

import domain.Actor;
import domain.Administrator;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.CheckUtils;

@Service
@Transactional
public class AdministratorService {
	@Autowired private AdministratorRepository repository;
	@Autowired private ActorService actorService;

	public Administrator findPrincipal()
	{
		Actor principal = actorService.findPrincipal();
		if (principal instanceof Administrator) {
			return (Administrator) principal;
		}
		return null;
	}

	public Administrator getPrincipal()
	{
		Actor principal = actorService.findPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		return (Administrator) principal;
	}

	public double findAvgAntennaCountPerUser()
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return repository.findAvgAntennaCountPerUser();
	}

	public double findStdDevAntennaCountPerUser()
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return repository.findStdDevAntennaCountPerUser();
	}

	public List<Object[]> findAntennaCountPerModel()
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return repository.findAntennaCountPerModel();
	}

	public List<Object[]> findMostPopularAntennas()
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return repository.findMostPopularAntennas(new PageRequest(0, 3)).getContent();
	}

	public double findAvgAntennaSignalQuality()
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return repository.findAvgAntennaSignalQuality();
	}

	public double findStdDevAntennaSignalQuality()
	{
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return repository.findStdDevAntennaSignalQuality();
	}
}