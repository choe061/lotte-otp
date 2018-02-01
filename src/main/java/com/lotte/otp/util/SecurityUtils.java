package com.lotte.otp.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by choi on 2018. 1. 30. PM 10:19.
 */
public class SecurityUtils {

    /**
     * 비밀번호 암호화
     * @param pw
     * @return
     */
    public static String passwordEncoder(String pw) {
        return new BCryptPasswordEncoder().encode(pw);
    }

    /**
     * 암호화된 비밀번호 검증
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    public static boolean isValidationPassword(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    /**
     * 카카오 플친과 최초 연동을 위한 임시키를 발급해주는 메소드
     * 사용자가 입력해야 하는 임시키이기 때문에 너무 복잡하게 출력하지 않음
     * @return
     */
    public static int distributeTempkey() {
        int tempKey = 0;
        for (int i = 0; i < 6; i++) {
            tempKey += (int) (Math.random() * 10) * Math.pow(10, i);
        }
        return tempKey;
    }

}
