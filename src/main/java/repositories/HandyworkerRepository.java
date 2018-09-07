
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Handyworker;
import domain.MaintenanceRequest;

@Repository
public interface HandyworkerRepository extends JpaRepository<Handyworker, Integer> {

	@Query("select h from Handyworker h where h.userAccount.id=?1")
	Handyworker findByUserAccount(int userAccount);

	@Query("select mr from MaintenanceRequest mr where mr.handyworker = ?1 and mr.doneTime IS NULL")
	List<MaintenanceRequest> findNotServedMaintenanceRequest(Handyworker worker);

	@Query("select mr from MaintenanceRequest mr where mr.handyworker = ?1 and mr.doneTime IS NOT NULL")
	List<MaintenanceRequest> findServedMaintenanceRequest(Handyworker worker);
}
