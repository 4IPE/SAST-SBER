package ru.SberTex.SastManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.SberTex.SastManager.enumeration.RoleName;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, представляющий сущность роли пользователя в системе.
 *
 * <p>Каждая роль может быть связана с несколькими пользователями через таблицу
 * "roles_users". Роли определяются с использованием перечисления {@link RoleName}.
 * </p>
 *
 * @author Даниил
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    /**
     * Уникальный идентификатор роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название роли, представленное как перечисление {@link RoleName}.
     */
    @Column
    @Enumerated(EnumType.STRING)
    private RoleName role;

    /**
     * У одного пользователя может быть только одна роль.
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users = new HashSet<User>();
}
