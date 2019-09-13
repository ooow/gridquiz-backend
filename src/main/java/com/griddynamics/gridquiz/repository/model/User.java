package com.griddynamics.gridquiz.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.griddynamics.gridquiz.rest.auth.Role;
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
    @JsonIgnore
    private String id;
    private String name;
    private String email;
    private String phone;
    private Role role;
}
