package com.lotte.otp.exception;

/**
 * Created by choi on 2018. 2. 4. PM 9:35.
 */
public class GenerateOtpException extends LotteServiceException {
    public GenerateOtpException() {
        super(404, "OTP 생성에 실패했습니다.");
    }
}
