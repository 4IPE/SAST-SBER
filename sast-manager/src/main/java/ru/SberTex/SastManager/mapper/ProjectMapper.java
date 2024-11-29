package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastManager.model.Project;

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
public interface ProjectMapper {

    /**
     * Преобразует объект Project в ProjectOutDto.
     *
     * @param project проект, который нужно преобразовать
     * @return объект ProjectOutDto
     */
    ProjectOutDto toProjectOutDto(Project project);

    /**
     * Преобразует множество объектов Project в множество ProjectOutDto.
     *
     * @param project проекты для преобразования
     * @return множество ProjectOutDto
     */
    Set<ProjectOutDto> toSetProjectOutDto(Set<Project> project);

    /**
     * Преобразует список объектов Project в список ProjectOutDto.
     *
     * @param project проекты для преобразования
     * @return список ProjectOutDto
     */
    List<ProjectOutDto> toListProjectOutDto(List<Project> project);

    /**
     * Преобразует объект ProjectDto в Project, игнорируя поле timeCreate.
     *
     * @param projectDto DTO проекта для преобразования
     * @return объект Project
     */
    @Mapping(target = "createdAt", ignore = true)
    Project toProject(ProjectDto projectDto);

    @Mapping(target = "timeCreate", ignore = true)
    Project toProject(ProjectOutDto projectDto);
}
