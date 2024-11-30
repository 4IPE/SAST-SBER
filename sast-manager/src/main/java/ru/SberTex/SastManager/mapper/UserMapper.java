package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.UserOutDto;
import ru.SberTex.SastDto.model.UserSingUpDto;
import ru.SberTex.SastManager.model.User;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ReportMapper.class, UserMapper.class, ProjectMapper.class})
public interface UserMapper {

    /**
     * Преобразует объект UserDto в User.
     *
     * @param userSingInDto DTO пользователя для преобразования
     * @return объект User
     */
    User toUser(UserSingUpDto userSingInDto);

    UserOutDto toUserOutDto(User user);

}
