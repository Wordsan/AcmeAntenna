package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import domain.Platform;
import domain.Satellite;

@Repository
public interface SatelliteRepository
extends JpaRepository<Satellite, Integer> {
    @Query("select s from Satellite s where s.deleted = false order by s.name asc")
    List<Satellite> findAllForIndex();


    // For tests:
    Satellite findByName(String name);
}