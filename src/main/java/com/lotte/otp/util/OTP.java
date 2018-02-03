package com.lotte.otp.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * Created by choi on 2018. 2. 4. AM 1:20.
 */
public class OTP {
    private static final long DISTANCE = 60 * 1000;     //1분
    private static final String ALGORITHM = "HmacSHA1";

    private static long create(String secretKey, long time) throws Exception {
        byte[] data = new byte[8];
        long value = time;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }

        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(new SecretKeySpec(secretKey.getBytes(), ALGORITHM));

        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;

        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= hash[offset + i] & 0xFF;
        }

        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;

        return truncatedHash;
    }

    /**
     * OTP 생성
     * TODO 예외 던지기 수정,보완
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String create(String secretKey) throws Exception {
        return String.format("%06d", create(secretKey, new Date().getTime() / DISTANCE));
    }

    /**
     * OTP 검증
     * TODO 예외 던지기 수정,보완
     * @param secretKey
     * @param code 웹에서 입력으로 들어온 OTP
     * @return
     * @throws Exception
     */
    public static boolean vertify(String secretKey, String code) throws Exception {
        return create(secretKey).equals(code);
    }
}
