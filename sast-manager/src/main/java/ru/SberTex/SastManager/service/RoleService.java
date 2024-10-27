package ru.SberTex.SastManager.service;

import ru.SberTex.SastDto.model.RoleDto;
import ru.SberTex.SastDto.model.UserDto;
import ru.SberTex.SastDto.model.UserOutDto;
import ru.SberTex.SastManager.model.Role;

public interface RoleService {

    void saveRole(RoleDto role);
    void updRole(RoleDto role);
    UserOutDto updRolesUser(RoleDto role, UserDto userDto);
}
