package com.griddynamics.gridquiz.repository.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    private String name;

    private String email;

    private String phone;

    private Role role;

    private String token;
}
