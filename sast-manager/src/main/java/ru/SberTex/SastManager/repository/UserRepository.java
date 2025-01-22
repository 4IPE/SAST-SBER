package ru.SberTex.SastManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.SberTex.SastManager.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);
}
