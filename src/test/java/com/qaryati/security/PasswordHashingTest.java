package com.qaryati.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordHashingTest {

    @Test
    void adminPasswordMatchesHashInDatabase() {
        String hash = "$2a$10$hwqCoxQAWfPZvNcPzW2Y5.JQRzeEPPHspA/JFMED72bi6f1ACAL2O";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches("admin123", hash), "The admin password hash in the database migration does not match 'admin123'");
    }
}
