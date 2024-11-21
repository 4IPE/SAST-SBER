package ru.SberTex.SastManager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.SberTex.SastDto.model.RoleDto;
import ru.SberTex.SastManager.service.RoleService;

/**
 * Контроллер для управления ролями пользователей.
 * Предоставляет API для сохранения, обновления ролей и назначения ролей пользователям.
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RoleController {
    private final RoleService roleService;

    /**
     * Сохранение новой роли.
     *
     * @param role Объект RoleDto для сохранения роли.
     * @return Статус успешного сохранения.
     */
    @PostMapping
    public ResponseEntity<String> saveRole(@RequestBody @Valid RoleDto role) {
        roleService.saveRole(role);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Сохранение успешно!");
    }

    /**
     * Обновление существующей роли.
     *
     * @param role Объект RoleDto с обновленными данными.
     * @return Статус успешного обновления.
     */
    @PutMapping
    public ResponseEntity<String> updRole(@RequestBody @Valid RoleDto role) {
        roleService.updRole(role);
        return ResponseEntity.ok().body("Обновление прошло успешно!");
    }

    /**
     * Назначение новой роли пользователю.
     *
     * @param role    Объект RoleDto с данными роли.
     * @param userDto Объект UserDto с данными пользователя.
     * @return Объект UserOutDto с обновленными данными пользователя.
     */
//    @PatchMapping
//    public ResponseEntity<UserOutDto> updRolesUser(@RequestBody @Valid RoleDto role,
//                                                   @RequestBody @Valid UserDto userDto) {
//        log.info("Смена роли у пользователя: {} на роль: {}", userDto.toString(), role.toString());
//        return ResponseEntity.ok().body(roleService.updRolesUser(role, userDto));
//    }
}
