package com.lotte.otp.domain;

/**
 * Created by choi on 2018. 1. 31. PM 2:42.
 */
public enum UserAuthStatus {
    OK,             //정상 인증
    NO_LINKED_OTP,  //OTP 미연동 상태
    UNAUTHORIZED,   //인증 실패
    NOT_FOUND_USER; //유저(ID) 없음
}
