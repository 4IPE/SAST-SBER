package ru.SberTex.SastManager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.RoleDto;
import ru.SberTex.SastDto.model.UserDto;
import ru.SberTex.SastDto.model.UserOutDto;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    @Override
    public void saveRole(RoleDto role) {

    }

    @Override
    public void updRole(RoleDto role) {

    }

    @Override
    public UserOutDto updRolesUser(RoleDto role, UserDto userDto) {
        return null;
    }
}
