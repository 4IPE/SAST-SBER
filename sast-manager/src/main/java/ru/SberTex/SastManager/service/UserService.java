/**
 * Интерфейс UserService предоставляет методы для управления пользователями.
 * Обеспечивает функциональность для сохранения и обновления данных пользователей.
 * <p>
 * Created by Daniil in 2024.
 */

package ru.SberTex.SastManager.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.SberTex.SastDto.model.UserOutDto;
import ru.SberTex.SastDto.model.UserUpdateDto;
import ru.SberTex.SastManager.model.User;

public interface UserService extends UserDetailsService {

    User getUserByUsername(String username);

    User getUserByUsernameOrEmail(String username);

//    UserDetailsService userDetailsService();

    User save(User user);

    boolean checkUser(String username);

    User getUserWithCookie(HttpServletRequest request);

    ResponseEntity<String> validCookies(HttpServletRequest request);

    void updateUserProfile(UserUpdateDto userUpdateDto, HttpServletRequest request);

    User getUserWithId(Long id);


    void requestForEditPassword(String email) throws MessagingException;

    void editPassword(String token, String password);
}
