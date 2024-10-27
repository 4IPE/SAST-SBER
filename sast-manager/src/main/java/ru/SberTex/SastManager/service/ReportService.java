package ru.SberTex.SastManager.service;

import ru.SberTex.SastDto.model.ReportDto;
import ru.SberTex.SastDto.model.ReportOutDto;

public interface ReportService {

    ReportOutDto saveProjectReports(ReportDto reportDto);
}
