package ru.SberTex.SastManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.SberTex.SastManager.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findById(Long id, Pageable pageable);
    Project findByUrl(String url);
}
