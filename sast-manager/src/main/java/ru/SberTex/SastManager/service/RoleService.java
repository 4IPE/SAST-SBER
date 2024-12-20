/**
 * Интерфейс RoleService предоставляет методы для управления ролями пользователей.
 * Включает в себя сохранение, обновление ролей и управление ролями пользователей.
 *
 * @author Даниил
 * @version 1.0
 * @since 2024
 */

package ru.SberTex.SastManager.service;

import ru.SberTex.SastDto.model.RoleDto;
import ru.SberTex.SastDto.enumeration.RoleName;
import ru.SberTex.SastManager.model.Role;

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


    Role getRoleWithName(RoleName name);
}
