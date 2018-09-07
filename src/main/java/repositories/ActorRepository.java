package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import security.UserAccount;

@Repository
public interface ActorRepository
        extends JpaRepository<Actor, Integer>
{
    Actor findByUserAccount(UserAccount userAccount);

    @Query("select a from Actor a where a.userAccount.username = ?1")
    Actor findByUsername(String username);
}