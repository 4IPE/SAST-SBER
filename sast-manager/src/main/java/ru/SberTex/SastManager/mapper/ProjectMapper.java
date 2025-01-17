package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectInfoDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastManager.model.Project;
import ru.SberTex.SastManager.model.User;
import ru.SberTex.SastManager.service.UserService;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс для маппинга объектов класса Project и его DTO.
 *
 * <p>Содержит методы для преобразования сущностей Project в различные представления DTO и наоборот.
 * Включает параметры для игнорирования нецелевых полей.</p>
 *
 * @author Даниил
 * @version 1.0
 * @see Project
 * @see ProjectDto
 * @see ProjectOutDto
 * @see ReportMapper
 * @see UserMapper
 *
 * <p>Основной используемый компонент - Spring.</p>
 * @since 2024
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {ReportMapper.class, UserMapper.class})

public abstract class ProjectMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private ReportMapper reportMapper;

    public abstract ProjectOutDto toProjectOutDto(Project project);

    public abstract ProjectInfoDto projectInfoDto(Project project);


    @Mapping(target = "reports", ignore = true)
    public abstract Set<ProjectOutDto> toSetProjectOutDto(Set<Project> project);


    public abstract List<ProjectOutDto> toListProjectOutDto(List<Project> project);


    @Mapping(target = "createdAt", ignore = true)
    public abstract Project toProject(ProjectDto projectDto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "users", source = "userId", qualifiedByName = "mapIdToUser")
    public abstract Project toProject(ProjectOutDto projectDto);

    @Mapping(target = "id", source = "id")
    public abstract ProjectDto toProjectDto(Project project);

    @Named("mapIdToUser")
    protected Set<User> mapIdToUser(Long id) {
        return Set.of(userService.getUserWithId(id));
    }
}