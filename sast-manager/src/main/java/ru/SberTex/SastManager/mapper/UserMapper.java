package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.UserDto;
import ru.SberTex.SastDto.model.UserOutDto;
import ru.SberTex.SastManager.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toUser(UserDto userDto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "toSetRoleDto")
    @Mapping(source = "projects", target = "projects", qualifiedByName = "toSetProjectOutDto")
    UserOutDto toUserOutDto(User user);
}
