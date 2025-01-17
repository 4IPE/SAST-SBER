package ru.SberTex.SastManager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", unique = true, nullable = false)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "teammate_id", referencedColumnName = "id", nullable = false)
    private Set<User> teammate;
}
