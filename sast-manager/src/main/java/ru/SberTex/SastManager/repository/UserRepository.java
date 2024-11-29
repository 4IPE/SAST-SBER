package ru.SberTex.SastManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.SberTex.SastManager.model.Project;
import ru.SberTex.SastManager.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u.projects FROM User u WHERE u.id = :userId")
    List<Project> findProjectsByUserId(@Param("userId") Long userId);

}
