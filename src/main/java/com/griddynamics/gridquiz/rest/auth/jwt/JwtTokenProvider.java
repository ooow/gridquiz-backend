package com.griddynamics.gridquiz.rest.auth.jwt;

import com.griddynamics.gridquiz.repository.model.User;
import com.griddynamics.gridquiz.rest.auth.CustomUserDetailsService;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(User user) {
        //TODO: Generate normal token.
        String token = user.getUsername() + "LOL" + new Date().toString() + Arrays.toString(
                user.getRoles().toArray());
        return token;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                                                       userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        //TODO: Normally parse the token.
        return token.substring(0, token.indexOf("LOL"));
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        return true;
        //TODO: Validate token.

        //        try {
        //            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        //            if (claims.getBody().getExpiration().before(new Date())) {
        //                return false;
        //            }
        //            return true;
        //        } catch (JwtException | IllegalArgumentException e) {
        //            throw new JwtException("Expired or invalid JWT token");
        //        }
    }
}
