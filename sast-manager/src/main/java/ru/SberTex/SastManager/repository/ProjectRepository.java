package ru.SberTex.SastManager.repository;

import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.SberTex.SastManager.model.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findById(Long id, Pageable pageable);

    List<Project> findByTeam_Teammates_Id(Long teammateId);

    boolean existsByUrl(String url);

    Project findByUrl(String url);

}
