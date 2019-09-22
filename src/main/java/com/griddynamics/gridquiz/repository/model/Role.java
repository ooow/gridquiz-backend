package com.griddynamics.gridquiz.repository.model;

import static com.griddynamics.gridquiz.repository.model.Role.Enum.ADMIN;
import static com.griddynamics.gridquiz.repository.model.Role.Enum.USER;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    private String id;
    private Enum role;

    @Override
    public String getAuthority() {
        return role.toString();
    }

    public enum Enum {
        ADMIN,
        USER,
    }

    public static Role.Enum getHighestRole(Set<Role> roles) {
        if (roles.stream().map(Role::getRole).anyMatch(ADMIN::equals)) {
            return ADMIN;
        }
        return USER;
    }
}
