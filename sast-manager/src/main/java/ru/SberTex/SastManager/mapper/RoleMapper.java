package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.RoleDto;
import ru.SberTex.SastManager.model.Role;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    Role toRole(RoleDto roleDto);
    RoleDto toRoleDto(Role role);

    Set<RoleDto> toSetRoleDto(Set<Role> role);
}