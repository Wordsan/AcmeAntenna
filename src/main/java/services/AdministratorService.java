package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import javax.transaction.Transactional;

import domain.Actor;
import domain.Administrator;
import domain.User;
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
        if (!LoginService.isAuthenticated()) return null;

        UserAccount userAccount = LoginService.getPrincipal();
        if (userAccount == null) return null;

        return repository.findByUserAccount(userAccount);
    }

    public Administrator getPrincipal()
    {
        Administrator principal = findPrincipal();
        Assert.isTrue(principal != null);
        return principal;
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

    public double findAvgTutorialCountPerUser()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        return repository.findAvgTutorialCountPerUser();
    }

    public double findStdDevTutorialCountPerUser()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        return repository.findStdDevTutorialCountPerUser();
    }

    public double findAvgCommentCountPerTutorial()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        return repository.findAvgCommentCountPerTutorial();
    }

    public double findStdDevCommentCountPerTutorial()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        return repository.findStdDevCommentCountPerTutorial();
    }

    public List<User> findTopTutorialContributors()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        return repository.findTopTutorialContributors();
    }

}