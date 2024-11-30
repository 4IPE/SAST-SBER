package ru.SberTex.SastAgent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastDto.model.ReportOutDto;

import java.util.Set;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {ReportMapper.class})
public interface ProjectMapper {
    /**
     * Преобразует объект  в ProjectOutDto.
     *
     * @param projectDto проект, который нужно преобразовать
     * @param reports отчёты
     * @return объект ProjectOutDto
     */
    ProjectOutDto toProjectOutDto(ProjectDto projectDto, Set<ReportOutDto> reports);
}