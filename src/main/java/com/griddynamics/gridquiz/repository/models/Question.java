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
@Entity(name = "questions")
public class Question implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1024)
    private String text;

    @Column
    private String title;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        TEXT,
        CODE,
        INPUT
    }

    @OneToMany
    @JoinColumn
    private List<Answer> answers;
}
