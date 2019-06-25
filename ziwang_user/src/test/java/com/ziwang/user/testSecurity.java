package com.ziwang.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class testSecurity {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "1234567z";
        String encyPassword = "$2a$10$IvOeB5YGqAyov9OgZ/7d0umn1lg8sNbh5azVUsfNmPouaTCe8nXDO";
        System.out.println(encoder.matches(password,encyPassword));
    }
}
