package services;

import org.apache.lucene.search.vectorhighlight.FieldFragList;
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
}