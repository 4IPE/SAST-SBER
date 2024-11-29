package ru.SberTex.SastManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.ReportDto;
import ru.SberTex.SastDto.model.ReportOutDto;
import ru.SberTex.SastManager.mapper.ReportMapper;
import ru.SberTex.SastManager.model.Report;
import ru.SberTex.SastManager.repository.ReportRepository;

import java.time.LocalDateTime;

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
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    @Override
    public ReportOutDto saveProjectReports(ReportDto reportDto) {
        Report report = reportMapper.toReport(reportDto);
        report.setCreatedAt(LocalDateTime.now().withSecond(0).withNano(0));
        return reportMapper.toReportOutDto(reportRepository.save(report));
    }

}
