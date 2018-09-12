package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import domain.Handyworker;
import domain.MaintenanceRequest;
import security.UserAccount;

@Repository
public interface HandyworkerRepository extends JpaRepository<Handyworker, Integer> {
    Handyworker findByUserAccount(UserAccount userAccount);

    @Query("select mr from MaintenanceRequest mr where mr.handyworker = ?1 and mr.doneTime IS NULL")
    List<MaintenanceRequest> findNotServedMaintenanceRequest(Handyworker worker);

    @Query("select mr from MaintenanceRequest mr where mr.handyworker = ?1 and mr.doneTime IS NOT NULL")
    List<MaintenanceRequest> findServedMaintenanceRequest(Handyworker worker);
}
