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
@Entity(name = "user_results")
public class UserResult implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Quiz quiz;

    @Column
    private Long points;

    @Column
    private Long startTime;

    @Column
    private Long endTime;

    @ManyToOne
    @JoinColumn
    private QuizResultMessage comment;

    public Long getId() {
        return id;
    }
}
