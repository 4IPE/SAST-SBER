package ru.SberTex.SastManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.SberTex.SastManager.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
