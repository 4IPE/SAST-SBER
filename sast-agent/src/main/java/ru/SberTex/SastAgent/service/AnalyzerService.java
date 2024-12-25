package ru.SberTex.SastAgent.service;

import ru.SberTex.SastAgent.SASTAnalyzer;
import ru.SberTex.SastDto.enumeration.Status;

public interface AnalyzerService {
    public StringBuilder getReportContent(SASTAnalyzer analyzer);
    public void patchReportStatus(Long reportId, Status status);
}
