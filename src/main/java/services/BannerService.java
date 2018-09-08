package services;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import domain.Agent;
import domain.Banner;
import domain.Platform;
import domain.Satellite;
import repositories.BannerRepository;
import security.Authority;
import utilities.CheckUtils;

@Service
@Transactional
public class BannerService {
    @Autowired private BannerRepository repository;
    @Autowired private AgentService agentService;

    public List<Banner> findAll()
    {
        return repository.findAll();
    }

    public List<Banner> findAllForIndex()
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        return findAll();
    }

    public Banner create(Banner banner)
    {
        CheckUtils.checkPrincipalAuthority(Authority.AGENT);
        CheckUtils.checkNotExists(banner);

        banner.setAgent(agentService.getPrincipal());

        return repository.save(banner);
    }

    public void delete(int id)
    {
        CheckUtils.checkPrincipalAuthority(Authority.ADMINISTRATOR);
        repository.delete(id);
    }

    public Banner randomBanner()
    {
        final List<Banner> banners = new ArrayList<Banner>(this.findAll());
        final Integer random = RandomUtils.nextInt(banners.size());
        return banners.get(random);
    }

    public List<Banner> findMyBanners()
    {
        CheckUtils.checkPrincipalAuthority(Authority.AGENT);
        Agent principal = agentService.getPrincipal();

        return repository.findByAgent(principal);
    }
}