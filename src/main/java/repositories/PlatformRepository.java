package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import domain.Platform;

@Repository
public interface PlatformRepository
extends JpaRepository<Platform, Integer> {
    List<Platform> findAllByOrderByNameAsc();
}