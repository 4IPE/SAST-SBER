package ru.SberTex.SastManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.ReportDto;
import ru.SberTex.SastDto.model.ReportOutDto;
import ru.SberTex.SastManager.mapper.ReportMapper;
import ru.SberTex.SastManager.model.Report;
import ru.SberTex.SastManager.repository.ReportsRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportsRepository reportsRepository;
    private final ReportMapper reportMapper;

    @Override
    public ReportOutDto saveProjectReports(ReportDto reportDto) {
        Report report = reportMapper.toReport(reportDto);
        report.setData(LocalDateTime.now().withSecond(0).withNano(0));
        return reportMapper.toReportOutDto(reportsRepository.save(report));
    }
}
