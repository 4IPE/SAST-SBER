/**
 * Интерфейс UserService предоставляет методы для управления пользователями.
 * Обеспечивает функциональность для сохранения и обновления данных пользователей.
 * <p>
 * Created by Daniil in 2024.
 */

package ru.SberTex.SastManager.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.SberTex.SastManager.model.User;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User save(User user);
}
