package ru.SberTex.SastManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.SberTex.SastManager.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
