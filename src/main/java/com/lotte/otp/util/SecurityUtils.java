package com.lotte.otp.util;

import com.lotte.otp.domain.UserConnectionQueueVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

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
        return BCrypt.hashpw(pw, BCrypt.gensalt());
    }

    /**
     * 암호화된 비밀번호 검증
     * @param plainPassword 평문
     * @param hashedPassword 암호화
     * @return
     */
    public static boolean isValidationPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /**
     * 카카오 플친과 최초 연동을 위한 임시키를 발급해주는 메소드
     * 사용자가 입력해야 하는 임시키이기 때문에 너무 복잡하게 출력하지 않음
     * @return
     */
    public static int distributeTempKey() {
        int tempKey = Integer.parseInt(RandomStringUtils.randomNumeric(6));
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
        if (requestTime - publishTime > expirationMin * 60 * 1000) {
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

    public static String getBrowser(String agent) {
        String browser = null;
        if (agent != null) {
            if (agent.indexOf("Trident") > -1) {
                browser = "MSIE";
            } else if (agent.indexOf("Chrome") > -1) {
                browser = "Chrome";
            } else if (agent.indexOf("Opera") > -1) {
                browser = "Opera";
            } else if (agent.indexOf("iPhone") > -1 && agent.indexOf("Mobile") > -1) {
                browser = "iPhone";
            } else if (agent.indexOf("Android") > -1 && agent.indexOf("Mobile") > -1) {
                browser = "Android";
            }
        }
        return browser;
    }

    public static String getOS(String agent) {
        String os = null;
        if(agent.indexOf("NT 6.0") != -1) os = "Windows Vista/Server 2008";
        else if(agent.indexOf("NT 5.2") != -1) os = "Windows Server 2003";
        else if(agent.indexOf("NT 5.1") != -1) os = "Windows XP";
        else if(agent.indexOf("NT 5.0") != -1) os = "Windows 2000";
        else if(agent.indexOf("NT") != -1) os = "Windows NT";
        else if(agent.indexOf("9x 4.90") != -1) os = "Windows Me";
        else if(agent.indexOf("98") != -1) os = "Windows 98";
        else if(agent.indexOf("95") != -1) os = "Windows 95";
        else if(agent.indexOf("Win16") != -1) os = "Windows 3.x";
        else if(agent.indexOf("Windows") != -1) os = "Windows";
        else if(agent.indexOf("Linux") != -1) os = "Linux";
        else if(agent.indexOf("Macintosh") != -1) os = "Macintosh";
        else os = "";
        return os;
    }

}
