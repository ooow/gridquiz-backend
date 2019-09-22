package com.griddynamics.gridquiz.rest.model;

import com.griddynamics.gridquiz.repository.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    private String id;
    private String name;
    private String email;
    private String phone;
    private Role.Enum role;
    private String token;
}
