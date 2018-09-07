
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.MaintenanceRequest;
import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserAccount(UserAccount userAccount);

	@Query("select mr from MaintenanceRequest mr where mr.user = ?1 and mr.doneTime IS NULL")
	List<MaintenanceRequest> findNotServedMaintenanceRequest(User user);

	@Query("select mr from MaintenanceRequest mr where mr.user = ?1 and mr.doneTime IS NOT NULL")
	List<MaintenanceRequest> findServedMaintenanceRequest(User user);
}
