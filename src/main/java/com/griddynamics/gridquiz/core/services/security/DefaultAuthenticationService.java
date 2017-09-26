package com.griddynamics.gridquiz.core.services.security;

import com.griddynamics.gridquiz.core.services.AuthenticationService;
import com.griddynamics.gridquiz.repository.UserDao;
import com.griddynamics.gridquiz.repository.models.Role;
import com.griddynamics.gridquiz.repository.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DefaultAuthenticationService implements AuthenticationService {

    @Autowired
    UserDao userDao;

    @Override
    public User authUser(User user) {
        User authUser = null;
        if (!"".equals(user.getPhone())) {
            authUser = userDao.findByPhone(user.getPhone());
        }
        if (Objects.isNull(authUser)) {
            if (!"".equals(user.getEmail())) {
                authUser = userDao.findByEmail(user.getEmail());
            }
            if (Objects.isNull(authUser)) {
                authUser = registerUser(user);
            }
            if (Role.ADMIN.equals(authUser.getRole())) {
                authUser.setToken(TokenGenerator.generateToken("eooYPagGx2"));
                userDao.save(authUser);
            }
        }
        return authUser;
    }

    private User registerUser(User user) {
        if ("admin".equals(user.getName())) {
            return user;
        }
        User authUser = new User();

        authUser.setName(user.getName());
        if (!"".equals(user.getEmail())) {
            authUser.setEmail(user.getEmail());
        }
        if (!"".equals(user.getPhone())) {
            authUser.setPhone(user.getPhone());
        }
        authUser.setRole(Role.USER);
        String username = user.getEmail() + user.getPhone();
        authUser.setToken(TokenGenerator.generateToken(username));
        userDao.save(authUser);

        return authUser;
    }
}
