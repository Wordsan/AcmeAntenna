package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import domain.Platform;
import domain.PlatformSubscription;
import domain.User;

@Repository
public interface PlatformSubscriptionRepository
extends JpaRepository<PlatformSubscription, Integer> {
    List<PlatformSubscription> findAllByUserAndPlatformOrderByIdDesc(User user, Platform platform);

    @Query("select ps from PlatformSubscription ps where ps.user = ?1 and ps.platform = ?2 and ps.endDate >= ?3 and ps.startDate <= ?4")
    List<PlatformSubscription> findOverlapping(User user, Platform platform, Date startDate, Date endDate);
}