package com.griddynamics.gridquiz.repository.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "quizzes")
public class Quiz implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private int result;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Long getId() {
        return id;
    }
}
