package ru.SberTex.SastManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.UserDto;
import ru.SberTex.SastDto.model.UserOutDto;
/**
 * Сервисный класс для управления пользователями.
 *
 * @see ru.SberTex.SastManager.service.UserService
 * @see ru.SberTex.SastDto.model.UserDto
 * @see ru.SberTex.SastDto.model.UserOutDto
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public UserOutDto saveUser(UserDto object) {
        return null;
    }

    @Override
    public void updateUser(UserDto object) {

    }
}
