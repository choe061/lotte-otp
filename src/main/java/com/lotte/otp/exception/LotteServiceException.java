package com.lotte.otp.exception;

/**
 * Created by choi on 2018. 1. 29. PM 1:28.
 */
public class LotteServiceException extends RuntimeException {
    protected int errorCode = -1;
    protected static final int CHECKED_EXCEPTION = 99999;

    public LotteServiceException() {
        super();
    }

    public LotteServiceException(int errorCode, String message) {
        super(errorCode + ". " + message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
