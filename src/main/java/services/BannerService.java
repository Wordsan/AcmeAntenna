
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	@Autowired
	private BannerRepository		bannerRepository;
	@Autowired
	private AgentService			agentService;
	@Autowired
	private AdministratorService	administratorService;


	public BannerService() {
		super();
	}

	public Banner create() {
		final Banner res = new Banner();

		res.setTargetPage("");
		res.setAgent(this.agentService.findPrincipal());

		return res;
	}

	public Collection<Banner> findAll() {
		final Collection<Banner> res = this.bannerRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Banner findOne(final int Id) {
		final Banner res = this.bannerRepository.findOne(Id);
		Assert.notNull(res);

		return res;
	}

	public Banner save(final Banner banner) {
		Assert.notNull(banner);
		Assert.notNull(banner.getAgent());
		final Banner res = this.bannerRepository.save(banner);

		return res;
	}

	public void delete(final Banner banner) {
		Assert.notNull(banner);
		Assert.isTrue(banner.getId() != 0);
		Assert.isTrue(banner.getAgent().equals(this.agentService.findPrincipal()) || this.administratorService.findPrincipal() != null);
		this.bannerRepository.delete(banner);
	}

	public Banner randomBanner() {
		final List<Banner> banners = new ArrayList<Banner>(this.findAll());
		final Integer random = RandomUtils.nextInt(banners.size());
		return banners.get(random);
	}

}
