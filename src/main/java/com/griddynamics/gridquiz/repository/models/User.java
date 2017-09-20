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
@Entity(name = "users")
public class User implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private Role role;

    @Column
    private String token;

    public Long getId() {
        return id;
    }
}
