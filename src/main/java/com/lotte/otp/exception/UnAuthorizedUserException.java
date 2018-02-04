package com.lotte.otp.exception;

/**
 * Created by choi on 2018. 2. 3. PM 10:52.
 */
public class UnAuthorizedUserException extends LotteServiceException {
    public UnAuthorizedUserException() {
        super(401, "키 인증에 실패 했습니다.");
    }
}
