package com.griddynamics.gridquiz.rest.auth;

import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.User;
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

    @PostMapping(value = "/user")
    @ResponseBody
    public User authUser(@RequestBody User user) {
        // TODO: Implement authorization.
        return userRepository.findByEmail(user.getEmail()).orElse(userRepository.save(user));
    }
}
