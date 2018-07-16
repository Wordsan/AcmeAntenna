package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import security.UserAccount;

@Repository
public interface AdministratorRepository
extends JpaRepository<Administrator, Integer> {
    Administrator findByUserAccount(UserAccount userAccount);
}