package ru.SberTex.SastManager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.SberTex.SastDto.model.RoleDto;
import ru.SberTex.SastDto.model.UserSingInDto;
import ru.SberTex.SastDto.enumeration.RoleName;
import ru.SberTex.SastManager.mapper.RoleMapper;
import ru.SberTex.SastManager.model.Role;
import ru.SberTex.SastManager.repository.RoleRepository;

/**
 * Сервисный класс для управления ролями.
 *
 * @see ru.SberTex.SastManager.service.RoleService
 * @see ru.SberTex.SastDto.model.RoleDto
 * @see UserSingInDto
 */

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    @Override
    public void saveRole(RoleDto role) {
        roleMapper.toRole(role);
    }

    @Override
    public void updRole(RoleDto role) {

    }

    @Override
    public Role getRoleWithName(RoleName name) {
        return roleRepository.findByRole(name);
    }

}
