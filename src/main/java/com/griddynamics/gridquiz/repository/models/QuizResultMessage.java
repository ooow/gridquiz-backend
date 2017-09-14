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
@ToString
@EqualsAndHashCode
@Entity(name = "quiz_result_messages")
public class QuizResultMessage implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Quiz quiz;

    @Column(nullable = false)
    private int rate;

    @Column(nullable = false)
    private String message;

    public Long getId() {
        return id;
    }
}
