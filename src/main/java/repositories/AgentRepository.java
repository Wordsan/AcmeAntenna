package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Agent;
import security.UserAccount;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {
    Agent findByUserAccount(UserAccount userAccount);
}
