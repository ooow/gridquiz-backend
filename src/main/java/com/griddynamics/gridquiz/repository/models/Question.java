package com.griddynamics.gridquiz.repository.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "questions")
public class Question implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1024)
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

    public Long getId() {
        return id;
    }
}
