
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	@Autowired
	private BannerRepository	bannerRepository;
	@Autowired
	private AgentService		agentService;


	public BannerService() {
		super();
	}

	public Banner create() {
		final Banner res = new Banner();
		final Collection<Banner> banners = this.findAll();
		final Integer max = this.maxPage(banners);
		final Integer targetPage = max + 1;
		res.setTargetPage(targetPage);
		res.setAgent(this.agentService.findPrincipal());

		return res;
	}

	private Integer maxPage(final Collection<Banner> banners) {
		Integer res = 0;
		for (final Banner b : banners)
			if (b.getTargetPage() > res)
				res = b.getTargetPage();

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

		final Banner res = this.bannerRepository.save(banner);

		return res;
	}

	public void delete(final Banner banner) {
		Assert.notNull(banner);
		Assert.isTrue(banner.getId() != 0);

		this.bannerRepository.delete(banner);
	}

}
