package com.griddynamics.gridquiz.repository.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;

@Getter
@Setter
@ToString(exclude = "correctly")
@EqualsAndHashCode
@Entity(name = "answers")
public class Answer implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @JsonIgnore
    @Column(nullable = false)
    private boolean correctly;

    @Column
    private boolean textField;

    public Long getId() {
        return id;
    }
}
