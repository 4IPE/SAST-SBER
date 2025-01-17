package ru.SberTex.SastManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.SberTex.SastManager.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findByTeammate_id(Long id);

}
