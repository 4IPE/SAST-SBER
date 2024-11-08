package ru.SberTex.SastManager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.SberTex.SastDto.model.UserDto;
import ru.SberTex.SastDto.model.UserOutDto;
import ru.SberTex.SastManager.model.User;

/**
 * Интерфейс для маппинга объектов класса User и его DTO.
 *
 * <p>Содержит методы для преобразования сущностей User в различные представления DTO и обратно.</p>
 *
 * @see User
 * @see UserDto
 * @see UserOutDto
 *
 * <p>Основной используемый компонент - Spring.</p>
 *
 * @version 1.0
 * @since 2024
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ReportMapper.class, UserMapper.class, ProjectMapper.class})
public interface UserMapper {

    /**
     * Преобразует объект UserDto в User.
     *
     * @param userDto DTO пользователя для преобразования
     * @return объект User
     */
    User toUser(UserDto userDto);

    /**
     * Преобразует объект User в UserOutDto.
     *
     * @param user пользователь для преобразования
     * @return объект UserOutDto
     */
    UserOutDto toUserOutDto(User user);
}
