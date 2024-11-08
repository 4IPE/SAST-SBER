/**
 * Интерфейс UserService предоставляет методы для управления пользователями.
 * Обеспечивает функциональность для сохранения и обновления данных пользователей.
 *
 * Created by Daniil in 2024.
 */

package ru.SberTex.SastManager.service;

import ru.SberTex.SastDto.model.UserDto;
import ru.SberTex.SastDto.model.UserOutDto;

public interface UserService {

    /**
     * Сохраняет нового пользователя.
     *
     * @param object данные пользователя в виде DTO
     * @return сохранённый пользователь в виде DTO
     */
    UserOutDto saveUser(UserDto object);

    /**
     * Обновляет данные существующего пользователя.
     *
     * @param object данные пользователя в виде DTO
     */
    void updateUser(UserDto object);
}
