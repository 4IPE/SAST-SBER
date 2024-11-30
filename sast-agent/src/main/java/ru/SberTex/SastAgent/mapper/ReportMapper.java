package ru.SberTex.SastAgent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.ReportOutDto;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {
    /**
     * Преобразует объект String в ReportOutDto.
     *
     * @param content отчет для преобразования
     * @param projectId идентификатор проекта
     * @return объект ReportOutDto
     */
    default ReportOutDto toReportOutDto(String content, Long projectId) {
        LocalDateTime now = LocalDateTime.now();
        return new ReportOutDto(content, now, projectId);
    }
}