package ru.SberTex.SastManager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Класс, представляющий проект в системе.
 *
 * <p>Проект содержит информацию о названии, URL, времени создания, а также связан с
 * пользователями и отчетами. Каждый проект может иметь несколько пользователей и отчетов.</p>
 *
 * @author Даниил
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project {

    /**
     * Уникальный идентификатор проекта.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название проекта.
     */
    @Column
    private String name;

    /**
     * URL проекта.
     */
    @Column
    private String url;

    /**
     * Дата и время создания проекта.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Множество пользователей, связанных с данным проектом.
     * Связь осуществляется через таблицу "projects_users".
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "projects_users",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users;

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", createdAt=" + createdAt +
                ", users=" + users +
                '}';
    }
}
