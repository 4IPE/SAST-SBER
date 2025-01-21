package ru.SberTex.SastManager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
@ToString
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
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Team team;

}
