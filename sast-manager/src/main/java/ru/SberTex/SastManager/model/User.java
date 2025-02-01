package ru.SberTex.SastManager.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Класс, представляющий пользователя в системе.
 *
 * <p>Пользователь может иметь несколько ролей и проектов. Каждая роль и проект
 * связаны с пользователем через соответствующие таблицы.</p>
 *
 * @author Даниил
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя, используемое для аутентификации.
     */
    @Column
    private String username;

    /**
     * Пароль пользователя для аутентификации.
     *
     * <p>Рекомендуется хранить пароль в зашифрованном виде.</p>
     */
    @Column
    private String password;

    @Column
    private String email;

    /**
     * Роль пользователя.
     */
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    @ManyToMany(mappedBy = "teammates")
    private List<Team> teams;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
