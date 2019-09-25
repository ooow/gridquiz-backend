package com.griddynamics.gridquiz.rest.model;

import static com.griddynamics.gridquiz.repository.model.Role.getHighestRole;

import com.griddynamics.gridquiz.repository.model.Role;
import com.griddynamics.gridquiz.repository.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private String id;
    private String name;
    private String email;
    private String phone;
    private Role.Enum role;
    private String token;

    public UserModel(User user, String token) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = getHighestRole(user.getRoles());
        this.token = token;
    }

    public UserModel(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = getHighestRole(user.getRoles());
    }
}
