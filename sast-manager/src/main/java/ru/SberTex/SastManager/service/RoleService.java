/**
 * Интерфейс RoleService предоставляет методы для управления ролями пользователей.
 * Включает в себя сохранение, обновление ролей и управление ролями пользователей.
 * @author Даниил
 * @version 1.0
 * @since 2024
 */

package ru.SberTex.SastManager.service;

import ru.SberTex.SastDto.model.RoleDto;
import ru.SberTex.SastDto.model.UserDto;
import ru.SberTex.SastDto.model.UserOutDto;

public interface RoleService {

    /**
     * Сохраняет новую роль.
     *
     * @param role данные роли в виде DTO
     */
    void saveRole(RoleDto role);

    /**
     * Обновляет существующую роль.
     *
     * @param role данные роли в виде DTO
     */
    void updRole(RoleDto role);

    /**
     * Обновляет роли, связанные с пользователем.
     *
     * @param role данные роли в виде DTO
     * @param userDto данные пользователя в виде DTO
     * @return обновленный пользователь в виде DTO
     */
    UserOutDto updRolesUser(RoleDto role, UserDto userDto);
}
