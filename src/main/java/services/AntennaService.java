package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import domain.Antenna;
import domain.User;
import repositories.AntennaRepository;
import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.CheckUtils;

@Service
@Transactional
public class AntennaService {
	@Autowired AntennaRepository repository;

	public Antenna create(Antenna submittedAntenna)
	{
		return repository.save(submittedAntenna);
	}
}