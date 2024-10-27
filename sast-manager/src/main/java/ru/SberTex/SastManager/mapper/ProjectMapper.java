package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastManager.model.Project;

import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {ReportMapper.class, UserMapper.class})
public interface ProjectMapper {
    @Mapping(target = "reports", source = "reports", qualifiedByName = "toSetReportOutDto")
    ProjectOutDto toProjectOutDto(Project project);

    @Named("toSetProjectOutDto")
    @Mapping(target = "reports", source = "reports", qualifiedByName = "toSetReportOutDto")
    Set<ProjectOutDto> toSetProjectOutDto(Set<Project> project);

    @Named("toListProjectOutDto")
    @Mapping(target = "reports", source = "reports", qualifiedByName = "toSetReportOutDto")
    List<ProjectOutDto> toListProjectOutDto(List<Project> project);

    @Mapping(target = "timeCreate",ignore = true)
    Project toProject(ProjectDto projectDto);
}
