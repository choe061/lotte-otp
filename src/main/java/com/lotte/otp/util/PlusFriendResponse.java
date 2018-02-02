package com.lotte.otp.util;

/**
 * Created by choi on 2018. 2. 2. PM 2:27.
 */
public class PlusFriendResponse {
    private PlusFriendResponse() {}

    public static final String REQUEST_CONNECT = "ID와 Web에서 발급받은 임시 키를 입력하세요.('/'로 구분)\n" +
                                                "Ex) honggildong/q1w2e3";

    public static final String OK = "회원님의 ID와 카카오 채팅 연동이 성공했습니다.";
    public static final String NO_CONETNT = "등록되지 않은 유저입니다.";
    public static final String UNAUTHORIZED = "ID와 임시키가 일치하는 정보가 없습니다.";
    public static final String TIME_OUT = "발급받은 임시키의 만료일시가 초과했습니다. Web상에서 다시 로그인해주세요.";

    //TIME_OUT일 경우 바로 발급해주지 않음 -> 보안상 더 안전하게
}
