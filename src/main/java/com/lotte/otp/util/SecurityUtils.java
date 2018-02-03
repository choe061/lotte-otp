package com.lotte.otp.util;

import com.lotte.otp.domain.UserConnectionQueueVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

/**
 * Created by choi on 2018. 1. 30. PM 10:19.
 */
public class SecurityUtils {

    private SecurityUtils() {}
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
    public static int distributeTempKey() {
        int tempKey = 0;
        for (int i = 0; i < 6; i++) {
            tempKey += (int) (Math.random() * 10) * Math.pow(10, i);
        }
        return tempKey;
    }

    /**
     * Key가 발급된 시간으로부터 만료시간이 지났는지 아닌지 확인해주는 메서드
     * @param publishTime 키 발급 시간
     * @param expirationMin 키 만료 시간(분 단위)
     * @return
     */
    public static boolean isTimeoutKey(long publishTime, int expirationMin) {
        Date now = new Date();
        long requestTime = now.getTime();
        if (requestTime - publishTime > expirationMin * 60 * 1000) {    //5분
            return true;
        }
        return false;
    }

    public static UserConnectionQueueVO tokenizeText(String text) {
        String[] keys = text.split("/");
        return new UserConnectionQueueVO(keys[0], Integer.parseInt(keys[1]));
    }

    public static String generateSecretKey() {
        return RandomStringUtils.randomAlphanumeric(255);
    }

}
