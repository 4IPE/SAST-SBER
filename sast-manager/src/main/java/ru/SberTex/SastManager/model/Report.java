package ru.SberTex.SastManager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.SberTex.SastDto.enumeration.Status;

import java.time.LocalDateTime;

/**
 * Класс, представляющий отчет в системе.
 *
 * <p>Отчет связан с проектом и содержит информацию о файле отчета и дате его создания.</p>
 *
 * @author Даниил
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "reports")
@Getter
@Setter
public class Report {

    /**
     * Уникальный идентификатор отчета.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Файл, представляющий отчет.
     *
     * <p>Следует учитывать, что работа с файлами может потребовать дополнительной обработки,
     * так как объект File не сохраняется в базе данных. Рекомендуется хранить путь к файлу
     * в виде строки.</p>
     */
    @Column(name = "file")
    private String content;

    /**
     * Дата и время создания отчета.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Проект, к которому принадлежит отчет.
     */
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", file='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", project=" + project +
                '}';
    }
}
