package com.griddynamics.gridquiz.rest.auth;

import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.User;
import com.griddynamics.gridquiz.rest.auth.jwt.JwtTokenProvider;
import com.griddynamics.gridquiz.rest.model.UserModel;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @ResponseBody
    public UserModel login(@RequestBody UserModel userModel) {
        User user;
        Optional<User> registered = userRepository.findByEmail(userModel.getEmail());
        user = registered.orElseGet(() -> service.saveUser(new User(userModel)));
        String token = jwtTokenProvider.createToken(user);
        UserModel registeredModel = user.toExternalUser();
        registeredModel.setToken(token);
        return registeredModel;
    }
}
