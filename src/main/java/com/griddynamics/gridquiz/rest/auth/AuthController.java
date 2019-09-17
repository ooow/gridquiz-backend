package com.griddynamics.gridquiz.rest.auth;

import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.UserInternal;
import com.griddynamics.gridquiz.rest.auth.jwt.JwtTokenProvider;
import com.griddynamics.gridquiz.rest.model.Response;
import com.griddynamics.gridquiz.rest.model.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @ResponseBody
    public Response login(@RequestBody User newUser) {
        Optional<UserInternal> userInternal = userRepository.findByEmail(newUser.getEmail());
        if (userInternal.isPresent()) {
            UserInternal user = userInternal.get();
            String username = user.getEmail();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, username));
            String token = jwtTokenProvider.createToken(user);
            return Response.builder().user(user.toExternalUser()).message(token).build();
        }
        UserInternal user = service.saveUser(new UserInternal(newUser));
        String token = jwtTokenProvider.createToken(user);
        return Response.builder().user(user.toExternalUser()).message(token).build();
    }
}
