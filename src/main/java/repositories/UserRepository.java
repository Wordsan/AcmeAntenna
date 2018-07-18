package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.User;
import security.UserAccount;

@Repository
public interface UserRepository
extends JpaRepository<User, Integer> {
    User findByUserAccount(UserAccount userAccount);
}