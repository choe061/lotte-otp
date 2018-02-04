package com.lotte.otp.exception;

/**
 * Created by choi on 2018. 2. 3. PM 10:53.
 */
public class TempKeyTimeOutException extends LotteServiceException {
    public TempKeyTimeOutException() {
        super(408, "키가 만료되었습니다.");
    }
}
