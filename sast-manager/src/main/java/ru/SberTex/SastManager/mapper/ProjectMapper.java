package ru.SberTex.SastManager.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectInfoDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastDto.model.ReportOutDto;
import ru.SberTex.SastManager.model.Project;
import ru.SberTex.SastManager.model.Report;
import ru.SberTex.SastManager.model.User;
import ru.SberTex.SastManager.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private  UserService userService;

    @Autowired
    private ReportMapper reportMapper;

    /**
     * Преобразует объект Project в ProjectOutDto.
     *
     * @param project проект, который нужно преобразовать
     * @return объект ProjectOutDto
     */
    public abstract ProjectOutDto toProjectOutDto(Project project);

    public abstract ProjectInfoDto projectInfoDto(Project project);

    /**
     * Преобразует множество объектов Project в множество ProjectOutDto.
     *
     * @param project проекты для преобразования
     * @return множество ProjectOutDto
     */
    @Mapping(target = "reports", ignore = true)
    public abstract Set<ProjectOutDto> toSetProjectOutDto(Set<Project> project);

    /**
     * Преобразует список объектов Project в список ProjectOutDto.
     *
     * @param project проекты для преобразования
     * @return список ProjectOutDto
     */
    public abstract List<ProjectOutDto> toListProjectOutDto(List<Project> project);

    /**
     * Преобразует объект ProjectDto в Project, игнорируя поле timeCreate.
     *
     * @param projectDto DTO проекта для преобразования
     * @return объект Project
     */
    @Mapping(target = "createdAt", ignore = true)
    public abstract Project toProject(ProjectDto projectDto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "users",source = "userId",qualifiedByName = "mapIdToUser")
    public abstract Project toProject(ProjectOutDto projectDto);

    @Mapping(target = "projectId", source = "id")
    public abstract ProjectDto toProjectDto(Project project);

    @Named("mapIdToUser")
    protected Set<User> mapIdToUser(Long id) {
        return Set.of(userService.getUserWithId(id));
    }
}