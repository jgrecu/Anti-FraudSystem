package eu.jgrecu.antifraud.repository;

import eu.jgrecu.antifraud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findUserByUsernameIgnoreCase(String username);
}
