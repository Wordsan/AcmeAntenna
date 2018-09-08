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
import utilities.CheckUtils;

@Service
@Transactional
public class AdministratorService {

    @Autowired
    private AdministratorRepository repository;
    @Autowired
    private ActorService actorService;


    public Administrator findPrincipal()
    {
        final Actor principal = this.actorService.findPrincipal();
        if (principal instanceof Administrator) {
            return (Administrator) principal;
        }
        return null;
    }

    public Administrator getPrincipal()
    {
        final Actor principal = this.actorService.findPrincipal();
        Assert.isTrue(principal instanceof Administrator);
        return (Administrator) principal;
    }

    public double findAvgAntennaCountPerUser()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findAvgAntennaCountPerUser();
        if (res != null) {
            return this.repository.findAvgAntennaCountPerUser();
        } else {
            return 0.0;
        }
    }

    public double findStdDevAntennaCountPerUser()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findStdDevAntennaCountPerUser();
        if (res != null) {
            return this.repository.findStdDevAntennaCountPerUser();
        } else {
            return 0.0;
        }
    }

    public List<Object[]> findAntennaCountPerModel()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        return this.repository.findAntennaCountPerModel();
    }

    public List<Object[]> findMostPopularAntennas()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        return this.repository.findMostPopularAntennas(new PageRequest(0, 3)).getContent();
    }

    public double findAvgAntennaSignalQuality()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findAvgAntennaSignalQuality();

        if (res != null) {
            return this.repository.findAvgAntennaSignalQuality();
        } else {
            return 0.0;
        }
    }

    public double findStdDevAntennaSignalQuality()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findStdDevAntennaSignalQuality();

        if (res != null) {
            return this.repository.findStdDevAntennaSignalQuality();
        } else {
            return 0.0;
        }
    }

    public double findAvgTutorialCountPerUser()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findAvgTutorialCountPerUser();

        if (res != null) {
            return this.repository.findAvgTutorialCountPerUser();
        } else {
            return 0.0;
        }
    }

    public double findStdDevTutorialCountPerUser()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findStdDevTutorialCountPerUser();

        if (res != null) {
            return this.repository.findStdDevTutorialCountPerUser();
        } else {
            return 0.0;
        }
    }

    public double findAvgCommentCountPerTutorial()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findAvgCommentCountPerTutorial();

        if (res != null) {
            return this.repository.findAvgCommentCountPerTutorial();
        } else {
            return 0.0;
        }
    }

    public double findStdDevCommentCountPerTutorial()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findStdDevCommentCountPerTutorial();

        if (res != null) {
            return this.repository.findStdDevCommentCountPerTutorial();
        } else {
            return 0.0;
        }
    }

    public List<User> findTopTutorialContributors()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        return this.repository.findTopTutorialContributors();
    }

    public double findAvgRequestCountPerUser()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findAvgRequestCountPerUser();

        if (res != null) {
            return this.repository.findAvgRequestCountPerUser();
        } else {
            return 0.0;
        }
    }

    public double findStdDevRequestCountPerUser()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findStdDevRequestCountPerUser();

        if (res != null) {
            return this.repository.findStdDevRequestCountPerUser();
        } else {
            return 0.0;
        }
    }

    public double findAvgRatioServicedRequestsPerUser()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findAvgRatioServicedRequestsPerUser();

        if (res != null) {
            return this.repository.findAvgRatioServicedRequestsPerUser();
        } else {
            return 0.0;
        }
    }

    public double findAvgRatioServicedRequestsPerHandyworker()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findAvgRatioServicedRequestsPerHandyworker();

        if (res != null) {
            return this.repository.findAvgRatioServicedRequestsPerHandyworker();
        } else {
            return 0.0;
        }
    }

    public double findAvgBannerCountPerAgent()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        final Double res = this.repository.findAvgBannerCountPerAgent();

        if (res != null) {
            return this.repository.findAvgBannerCountPerAgent();
        } else {
            return 0.0;
        }
    }

    public List<Object[]> findMostPopularAgentsByBanners()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        return this.repository.findMostPopularAgentsByBanners(new PageRequest(0, 3)).getContent();
    }

}
