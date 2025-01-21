package ru.SberTex.SastManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.SberTex.SastManager.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByTeammates_id(Long id);

    int countByTeammates_id(Long id);

    boolean existsByIdAndProject_Owner_Id(Long id, Long ownerId);
}
