package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.PlatformSubscription;

@Repository
public interface PlatformSubscriptionRepository
extends JpaRepository<PlatformSubscription, Integer> {
}