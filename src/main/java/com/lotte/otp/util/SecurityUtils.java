package com.lotte.otp.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by choi on 2018. 1. 30. PM 10:19.
 */
public class SecurityUtils {

    public static String passwordEncoder(String pw) {
        return new BCryptPasswordEncoder().encode(pw);
    }

    public static boolean isValidationPassword(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

}
