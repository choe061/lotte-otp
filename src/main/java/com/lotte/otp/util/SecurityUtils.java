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

    /**
     * 카카오 플친과 최초 연동을 위한 임시키를 발급해주는 메소드
     * 임시키이지만 토큰에 대한 정책을 확실히 정의...
     * @param id
     * @return
     */
    public static String distributeTempkey(String id) {
        return null;
    }

}
