package ru.SberTex.SastManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.SberTex.SastManager.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
