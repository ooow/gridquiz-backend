package com.griddynamics.gridquiz.rest.auth;

import static com.griddynamics.gridquiz.repository.model.Role.Enum.ADMIN;

import com.griddynamics.gridquiz.repository.RoleRepository;
import com.griddynamics.gridquiz.rest.auth.jwt.JwtConfigurer;
import com.griddynamics.gridquiz.rest.auth.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors();

        http.authorizeRequests()
                .antMatchers("/open/**", "/auth/**")
                .permitAll().antMatchers("/admin/**").hasAuthority(ADMIN.toString())
                .anyRequest()
                .authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling();

        http.apply(new JwtConfigurer(jwtTokenProvider));

        http.headers().cacheControl();
    }
}
