package com.lotte.otp.util;

import com.lotte.otp.exception.GenerateOtpException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by choi on 2018. 2. 4. AM 1:20.
 */
public class OTP {
    private static final long DISTANCE = 60 * 1000;     //1분
    private static final String ALGORITHM = "HmacSHA1";

    private static long create(String secretKey, long time) {
        byte[] data = new byte[8];
        long value = time;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }

        Mac mac = null;
        try {
            mac = Mac.getInstance(ALGORITHM);
            mac.init(new SecretKeySpec(secretKey.getBytes(), ALGORITHM));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new GenerateOtpException();
        }

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
     * @throws GenerateOtpException
     */
    public static String create(long time, String secretKey) throws GenerateOtpException {
        return String.format("%06d", create(secretKey, time / DISTANCE));
    }

    /**
     * OTP 검증
     * TODO 예외 던지기 수정,보완
     * @param secretKey
     * @param code 웹에서 입력으로 들어온 OTP
     * @return
     * @throws GenerateOtpException
     */
    public static boolean vertify(long time, String secretKey, String code) throws GenerateOtpException {
        return create(time, secretKey).equals(code);
    }
}
