package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import domain.Antenna;
import domain.Handyworker;
import domain.MaintenanceRequest;
import sun.applet.Main;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Integer> {
    @Query("select mr from MaintenanceRequest mr where mr.doneTime is null and mr.antenna = ?2 and mr.handyworker = ?1")
    List<MaintenanceRequest> findPendingRequests(Handyworker principal, Antenna antenna);
}
