package com.griddynamics.gridquiz.core.services.security;

import com.griddynamics.gridquiz.core.services.AuthenticationService;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.models.Role;
import com.griddynamics.gridquiz.repository.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthenticationService implements AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User authorize(User user) {
        User authorized = userRepository.findByPhone(user.getPhone())
                .orElse(userRepository.findByEmail(user.getEmail()).orElse(registerUser(user)));

        if (Role.ADMIN.equals(authorized.getRole())) {
            authorized.setToken(TokenGenerator.generateToken("eooYPagGx2"));
            userRepository.save(authorized);
        }

        return authorized;
    }

    private User registerUser(User user) {
        if ("admin".equals(user.getName())) {
            return user;
        }
        User authUser = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(Role.USER)
                .token(TokenGenerator.generateToken(
                        String.format("%s%s", user.getEmail(), user.getPhone())))
                .build();
        userRepository.save(authUser);

        return authUser;
    }
}
