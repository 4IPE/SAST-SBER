package ru.SberTex.SastManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.SberTex.SastManager.model.Report;

import java.util.List;


public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findAllByProject_idOrderByCreatedAtDesc(Long projectId);

}
