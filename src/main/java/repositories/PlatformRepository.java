package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import domain.Platform;

@Repository
public interface PlatformRepository
extends JpaRepository<Platform, Integer> {
    @Query("select p from Platform p where p.deleted = false order by p.name asc")
    List<Platform> findAllForIndex();
}