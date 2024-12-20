package ru.SberTex.SastManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.SberTex.SastDto.enumeration.RoleName;
import ru.SberTex.SastManager.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(RoleName name);
}
