package com.griddynamics.gridquiz.repository.model;

import static com.griddynamics.gridquiz.repository.model.Role.Enum.ADMIN;
import static com.griddynamics.gridquiz.repository.model.Role.Enum.USER;

import com.griddynamics.gridquiz.rest.model.UserModel;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistered implements UserDetails {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private Set<Role> roles;

    public UserRegistered(UserModel user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.phone = user.getPhone();
    }

    /** Converts internal user to external for sending to the client. */
    public UserModel toExternalUser() {
        return UserModel.builder()
                .id(id)
                .email(email)
                .name(name)
                .phone(phone)
                .role(getHighestRole())
                .build();
    }

    public List<GrantedAuthority> getAuthoritiesList() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole().toString()))
                .collect(Collectors.toList());
    }

    private Role.Enum getHighestRole() {
        if (roles.stream().map(Role::getRole).anyMatch(ADMIN::equals)) {
            return ADMIN;
        }
        return USER;
    }

    @Override
    public Set<Role> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
