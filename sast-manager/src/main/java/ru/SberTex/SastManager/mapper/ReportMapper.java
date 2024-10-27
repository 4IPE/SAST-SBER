package ru.SberTex.SastManager.mapper;

import jdk.jfr.Name;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.ReportDto;
import ru.SberTex.SastDto.model.ReportOutDto;
import ru.SberTex.SastManager.model.Report;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {
    @Mapping(target = "data",ignore = true)
    Report toReport(ReportDto report);

    ReportOutDto toReportOutDto(Report report);

    @Name("toSetReportOutDto")
    Set<ReportOutDto> toSetReportOutDto(Set<Report> reports);
}
