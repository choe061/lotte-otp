package com.lotte.otp.util;

/**
 * Created by choi on 2018. 2. 2. PM 2:27.
 */
public class ChattingText {
    private ChattingText() {}

    public static final String[] NO_MATCHING = {
            "지금 명령은 잘모르겠습니다. 아래 버튼을 이용해주세요!",
            "아래 3가지 버튼 중 원하시는 기능을 선택해주세요!",
            "버튼을 선택하시거나 버튼과 같은 메시지를 보내주세요!"
    };

    public static final String REGIST_ID_BUTTON = "ID 등록";
    public static final String REQUEST_OTP_BUTTON = "OTP (재)발급";
    public static final String OTP_EXPIRATION_TIME_BUTTON = "OTP 만료 시간 확인";
    public static final String LOGIN_HISTORY_BUTTON = "로그인 내역 확인";
}
