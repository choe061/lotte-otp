package com.lotte.otp.exception;

/**
 * Created by choi on 2018. 1. 29. PM 1:30.
 */
public class DuplicateUserIDException extends LotteServiceException {
    public DuplicateUserIDException() {
        super(409, "중복된 아이디입니다.");
    }
}
