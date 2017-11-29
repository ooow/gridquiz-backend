package com.griddynamics.gridquiz.repository.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "quizzes")
public class Quiz implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @OneToMany
    @JoinColumn
    private List<Color> colors;

    @OneToMany
    @JoinColumn
    private List<Question> questions;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        TEST,
        QUIZ
    }
}
