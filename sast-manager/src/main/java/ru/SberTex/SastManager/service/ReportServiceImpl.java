package ru.SberTex.SastManager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.SberTex.SastDto.enumeration.Status;
import ru.SberTex.SastDto.model.*;
import ru.SberTex.SastManager.kafka.KafkaProducer;
import ru.SberTex.SastManager.mapper.ReportMapper;
import ru.SberTex.SastManager.model.Project;
import ru.SberTex.SastManager.model.Report;
import ru.SberTex.SastManager.repository.ReportRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервисный класс для управления отчетами проектов.
 *
 * @see ru.SberTex.SastManager.service.ReportService
 * @see ru.SberTex.SastDto.model.ReportDto
 * @see ru.SberTex.SastDto.model.ReportOutDto
 * @see ru.SberTex.SastManager.mapper.ReportMapper
 * @see ReportRepository
 */

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final KafkaProducer producer;
    private final ProjectService projectService;

    @Override
    public void saveProjectReports(Set<ReportOutDto> reportDto, Project project) {
        Set<Report> reports = reportDto.stream().map(reportMapper::toReport).collect(Collectors.toSet());
        reports.forEach(report -> report.setProject(project));
        reportRepository.saveAll(reports);
    }

    @Override
    public List<ReportOutDto> getAllReportsWithProjectId(Long projectId) {
        return reportRepository.findAllByProject_idOrderByCreatedAtDesc(projectId).stream()
                .map(reportMapper::toReportOutDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createReport(ProjectDto object) {
        ReportDto reportDto = new ReportDto(null,"", object.getId(), Status.NEW);
        Report report = saveReportProject(reportDto);
        reportDto.setId(report.getId());
        log.info("IDDD{}",reportDto.getId());
        object.setReportDto(reportDto);
        producer.sendMessageInAgent(object);
    }

    @Override
    public void addReports(ProjectOutDto object) {
        Project project = projectService.getProjectWithId(object.id());
        this.saveProjectReports(object.reports(), project);
    }

    @Override
    public Report saveReportProject(ReportDto reportDto) {
        Report report = reportMapper.toReport(reportDto);
        report.setProject(projectService.getProjectWithId(reportDto.getProjectId()));
        report.setCreatedAt(LocalDateTime.now().withNano(0).withSecond(0));

        return reportRepository.save(report);
    }

    @Override
    public Report getReportWithId(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new RuntimeException("NotFoundReport"));
    }

    @Override
    public void updReportProjectStatus(ReportUpdateStatusDto upd) {
        Report report = getReportWithId(upd.id());
        report.setStatus(upd.status());
        reportRepository.save(report);
    }


}
