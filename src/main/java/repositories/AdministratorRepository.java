
package repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Administrator;
import domain.User;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	Administrator findByUserAccount(UserAccount userAccount);
	@Query("select avg(u.antennas.size) from User u")
	double findAvgAntennaCountPerUser();
	@Query("select stddev(u.antennas.size) from User u")
	double findStdDevAntennaCountPerUser();
	@Query("select x.model, count(x) from Antenna x group by x.model")
	List<Object[]> findAntennaCountPerModel();
	@Query("select x.model, count(x) from Antenna x group by x.model order by count(x) desc")
	Page<Object[]> findMostPopularAntennas(Pageable pageable);
	@Query("select avg(a.signalQuality) from Antenna a")
	double findAvgAntennaSignalQuality();
	@Query("select stddev(a.signalQuality) from Antenna a")
	double findStdDevAntennaSignalQuality();
	@Query("select avg(u.tutorials.size) from User u")
	double findAvgTutorialCountPerUser();
	@Query("select stddev(u.tutorials.size) from User u")
	double findStdDevTutorialCountPerUser();
	@Query("select avg(u.tutorialComments.size) from Tutorial u")
	double findAvgCommentCountPerTutorial();
	@Query("select stddev(u.tutorialComments.size) from Tutorial u")
	double findStdDevCommentCountPerTutorial();
	@Query("select u from User u where u.tutorials.size > (select avg(u.tutorials.size) + stddev(u.tutorials.size) from User u) order by u.tutorials.size desc")
	List<User> findTopTutorialContributors();

	@Query("select avg(u.requests.size) from User u")
	double findAvgRequestCountPerUser();
	@Query("select stddev(u.requests.size) from User u")
	double findStdDevRequestCountPerUser();
	@Query("select avg(u.requests.size) from User u join u.requests r where r.doneTime is not null")
	double findAvgRatioServicedRequestsPerUser();
	@Query("select avg(h.requests.size) from Handyworker h join h.requests r where r.doneTime is not null")
	double findAvgRatioServicedRequestsPerHandyworker();
	@Query("select avg(a.banners.size) from Agent a")
	double findAvgBannerCountPerAgent();
	@Query("select b.agent.name, count(b) from Banner b group by b.agent.name order by count(b) desc")
	Page<Object[]> findMostPopularAgentsByBanners(Pageable pageable);
}
