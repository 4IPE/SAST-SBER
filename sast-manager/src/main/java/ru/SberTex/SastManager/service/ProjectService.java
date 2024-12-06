/**
 * Интерфейс ProjectService предоставляет методы для управления проектами пользователей.
 * Предоставляет функциональность для получения и сохранения проектов.
 *
 * @author Даниил
 * @version 1.0
 * @since 2024
 */

package ru.SberTex.SastManager.service;

import jakarta.transaction.Transactional;
import ru.SberTex.SastDto.model.ProjectDto;
import ru.SberTex.SastDto.model.ProjectOutDto;
import ru.SberTex.SastManager.model.Project;

import java.util.List;

public interface ProjectService {

    /**
     * Получает все проекты, связанные с пользователем по его ID.
     *
     * @param id   идентификатор пользователя
     * @param from начальный индекс для пагинации
     * @param size количество элементов для получения
     * @return список проектов пользователя в виде DTO
     */
    List<ProjectOutDto> getAllUsersProject(Long id, Integer from, Integer size);

    @Transactional
    void saveUsersProject(ProjectDto object);

    Project getProjectWithId(Long id);
}
