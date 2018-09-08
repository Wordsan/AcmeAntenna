package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import domain.Agent;
import domain.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {
    List<Banner> findByAgent(Agent principal);
}
