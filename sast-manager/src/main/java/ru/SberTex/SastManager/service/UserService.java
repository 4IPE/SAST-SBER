package ru.SberTex.SastManager.service;

import ru.SberTex.SastDto.model.UserDto;
import ru.SberTex.SastDto.model.UserOutDto;


public interface UserService {

    UserOutDto saveUser(UserDto object);

    void updateUser(UserDto object);
}
