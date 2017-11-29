package com.griddynamics.gridquiz.core.services.security;

import com.griddynamics.gridquiz.core.services.AuthenticationService;
import com.griddynamics.gridquiz.repository.UserDao;
import com.griddynamics.gridquiz.repository.models.Role;
import com.griddynamics.gridquiz.repository.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class DefaultAuthenticationService implements AuthenticationService {

    @Autowired
    UserDao userDao;

    @Override
    public User authUser(User user) {
        User authUser;
        if (!user.getPhone().isEmpty()) {
            authUser = ofNullable(userDao.findByPhone(user.getPhone())).orElse(registerUser(user));
        } else {
            authUser = ofNullable(userDao.findByEmail(user.getEmail())).orElse(registerUser(user));
        }

        if (Role.ADMIN.equals(authUser.getRole())) {
            authUser.setToken(TokenGenerator.generateToken("eooYPagGx2"));
            userDao.save(authUser);
        }

        return authUser;
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
                .token(TokenGenerator.generateToken(String.format("%s%s", user.getEmail(), user.getPhone())))
                .build();
        userDao.save(authUser);

        return authUser;
    }
}
