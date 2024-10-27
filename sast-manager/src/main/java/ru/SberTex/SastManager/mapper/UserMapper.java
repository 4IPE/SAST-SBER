package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.UserDto;
import ru.SberTex.SastDto.model.UserOutDto;
import ru.SberTex.SastManager.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = {ReportMapper.class, UserMapper.class,ProjectMapper.class})
public interface UserMapper {
    User toUser(UserDto userDto);

    UserOutDto toUserOutDto(User user);
}
