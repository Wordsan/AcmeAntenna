package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import domain.Administrator;
import domain.User;
import security.UserAccount;

@Repository
public interface AdministratorRepository
extends JpaRepository<Administrator, Integer> {
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
}