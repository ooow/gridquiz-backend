package com.griddynamics.gridquiz.rest.auth;

import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.User;
import com.griddynamics.gridquiz.rest.auth.jwt.JwtTokenProvider;
import com.griddynamics.gridquiz.rest.model.UserModel;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @PostMapping("/login")
    @ResponseBody
    public UserModel login(@RequestBody UserModel userModel) {
        if (userModel.getName().isEmpty() || userModel.getEmail().isEmpty()) {
            throw new IllegalArgumentException(
                    "The 'name' and 'email' parameters must not be null or empty");
        }
        Optional<User> registered = userRepository.findByEmail(userModel.getEmail());
        User user = registered.orElseGet(() -> service.saveUser(new User(userModel)));
        String token = jwtTokenProvider.createToken(user);
        return new UserModel(user, token);
    }
}
