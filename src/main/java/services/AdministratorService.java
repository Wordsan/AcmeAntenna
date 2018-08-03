
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import utilities.CheckUtils;
import domain.Actor;
import domain.Administrator;
import domain.User;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	repository;
	@Autowired
	private ActorService			actorService;


	public Administrator findPrincipal() {
		final Actor principal = this.actorService.findPrincipal();
		if (principal instanceof Administrator)
			return (Administrator) principal;
		return null;
	}

	public Administrator getPrincipal() {
		final Actor principal = this.actorService.findPrincipal();
		Assert.isTrue(principal instanceof Administrator);
		return (Administrator) principal;
	}

	public void ban(final int actorId) {
		Assert.isTrue(actorId != 0);

		final Actor actor = this.actorService.findOne(actorId);
		Assert.notNull(actor);
		actor.setBanned(true);

		this.actorService.save(actor);
	}

	public void unban(final int actorId) {
		Assert.isTrue(actorId != 0);

		final Actor actor = this.actorService.findOne(actorId);
		Assert.notNull(actor);
		actor.setBanned(false);

		this.actorService.save(actor);
	}

	public double findAvgAntennaCountPerUser() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findAvgAntennaCountPerUser();
	}

	public double findStdDevAntennaCountPerUser() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findStdDevAntennaCountPerUser();
	}

	public List<Object[]> findAntennaCountPerModel() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findAntennaCountPerModel();
	}

	public List<Object[]> findMostPopularAntennas() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findMostPopularAntennas(new PageRequest(0, 3)).getContent();
	}

	public double findAvgAntennaSignalQuality() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findAvgAntennaSignalQuality();
	}

	public double findStdDevAntennaSignalQuality() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findStdDevAntennaSignalQuality();
	}

	public double findAvgTutorialCountPerUser() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findAvgTutorialCountPerUser();
	}

	public double findStdDevTutorialCountPerUser() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findStdDevTutorialCountPerUser();
	}

	public double findAvgCommentCountPerTutorial() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findAvgCommentCountPerTutorial();
	}

	public double findStdDevCommentCountPerTutorial() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findStdDevCommentCountPerTutorial();
	}

	public List<User> findTopTutorialContributors() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findTopTutorialContributors();
	}

	public double findAvgRequestCountPerUser() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findAvgRequestCountPerUser();
	}

	public double findStdDevRequestCountPerUser() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findStdDevRequestCountPerUser();
	}

	public double findAvgRatioServicedRequestsPerUser() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findAvgRatioServicedRequestsPerUser();
	}

	public double findAvgRatioServicedRequestsPerHandyworker() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findAvgRatioServicedRequestsPerHandyworker();
	}

	public double findAvgBannerCountPerAgent() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findAvgBannerCountPerAgent();
	}

	public List<Object[]> findMostPopularAgentsByBanners() {
		CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
		return this.repository.findMostPopularAgentsByBanners(new PageRequest(0, 3)).getContent();
	}

}
