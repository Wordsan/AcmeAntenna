package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import domain.Antenna;
import domain.User;

@Repository
public interface AntennaRepository
        extends JpaRepository<Antenna, Integer>
{
    List<Antenna> findAllByUserOrderBySerialNumberAsc(User user);

    // For tests:
    Antenna findBySerialNumber(String serialNumber);
}