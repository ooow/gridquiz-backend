package com.griddynamics.gridquiz.core.services.security;

import java.security.SecureRandom;

public class TokenGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String generateToken(String username) {
        long longToken = Math.abs(random.nextLong());
        String random = Long.toString(longToken, 16);
        return (username + "tr" + random);
    }
}
