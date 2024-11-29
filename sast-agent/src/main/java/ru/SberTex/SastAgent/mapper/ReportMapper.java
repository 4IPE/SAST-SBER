package ru.SberTex.SastAgent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastAgent.model.Report;
import ru.SberTex.SastDto.model.ReportOutDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {
    /**
     * Преобразует объект Report в ReportOutDto.
     *
     * @param report отчет для преобразования
     * @return объект ReportOutDto
     */
    ReportOutDto toReportOutDto(Report report);
}
