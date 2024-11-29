package ru.SberTex.SastManager.data;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.SberTex.SastManager.enumeration.RoleName;
import ru.SberTex.SastManager.model.Role;
import ru.SberTex.SastManager.repository.RoleRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;


    @PostConstruct
    public void init() {
        // Проверяем, если таблица пустая
        if (roleRepository.count() == 0) {
            // Создаем дефолтные продукты
            Role admin = new Role();
            Role user = new Role();
            admin.setRole(RoleName.ROLE_ADMIN);
            user.setRole(RoleName.ROLE_USER);
            List<Role> defaultRole = List.of(admin, user);

            // Сохраняем дефолтные продукты в базе
            roleRepository.saveAll(defaultRole);
            System.out.println("База данных инициализирована дефолтными ролями.");
        } else {
            System.out.println("Роли уже существуют в базе данных. Инициализация не требуется.");
        }
    }
}
