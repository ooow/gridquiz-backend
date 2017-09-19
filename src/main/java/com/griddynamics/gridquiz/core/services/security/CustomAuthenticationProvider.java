package com.griddynamics.gridquiz.core.services.security;

import com.griddynamics.gridquiz.repository.models.Role;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        //if (shouldAuthenticateAgainstThirdPartySystem()) {

        // use the credentials
        // and authenticate against the third-party system
        return new UsernamePasswordAuthenticationToken(name, password, buildUserAuthority(Role.USER));
        /*} else {
            return null;
        }*/
    }


    private List<GrantedAuthority> buildUserAuthority(Role userRole) {
        Set<GrantedAuthority> setAuth = new HashSet<>();
        setAuth.add(new SimpleGrantedAuthority(userRole.name()));
        return new ArrayList<>(setAuth);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
