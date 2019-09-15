package com.griddynamics.gridquiz.rest.auth;

import com.griddynamics.gridquiz.repository.RoleRepository;
import com.griddynamics.gridquiz.repository.UserRepository;
import com.griddynamics.gridquiz.repository.model.Role;
import com.griddynamics.gridquiz.repository.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    //    @Autowired
    //    private PasswordEncoder encoder;

    public User saveUser(User user) {
        Role role = roleRepository.findByRole("USER").get();
        user.setRoles(Set.of(role));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return buildUserForAuthentication(user.get(), user.get().getAuthoritiesList());
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                                                                      user.getPassword(),
                                                                      authorities);
    }
}
