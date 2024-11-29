package ru.SberTex.SastAgent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastAgent.model.Project;
import ru.SberTex.SastDto.model.ProjectOutDto;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {ReportMapper.class})
public interface ProjectMapper {
    /**
     * Преобразует объект Project в ProjectOutDto.
     *
     * @param project проект, который нужно преобразовать
     * @return объект ProjectOutDto
     */
    ProjectOutDto toProjectOutDto(Project project);

}
