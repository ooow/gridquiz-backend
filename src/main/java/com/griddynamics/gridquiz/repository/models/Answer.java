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
@Entity(name = "answers")
public class Answer implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private boolean correctly;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Question question;

    public Long getId() {
        return id;
    }
}
