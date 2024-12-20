package ru.SberTex.SastManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastDto.model.ReportOutDto;
import ru.SberTex.SastManager.kafka.KafkaProducer;
import ru.SberTex.SastManager.mapper.ReportMapper;
import ru.SberTex.SastManager.model.Project;
import ru.SberTex.SastManager.model.Report;
import ru.SberTex.SastManager.repository.ReportRepository;

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
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final KafkaProducer producer;
    private final ProjectService projectService;

    @Override
    public void saveProjectReports(Set<ReportOutDto> reportDto, Project project) {
        Set<Report> reports = reportDto.stream().map(reportMapper::toReport).collect(Collectors.toSet());
        reports.forEach(report->report.setProject(project));
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
        producer.sendMessageInAgent(object);
    }

    @Override
    public void addReports(ProjectOutDto object){
        Project project = projectService.getProjectWithId(object.projectId());
        this.saveProjectReports(object.reports(), project);
    }

}
