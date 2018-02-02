package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by choi on 2018. 2. 2. PM 3:59.
 */
@Getter
@AllArgsConstructor
public enum ChatBotStep {
    SUCCESS(3,
            "ID가 등록되었습니다.\n" +
                    "아래 버튼을 통해 OTP챗봇을 사용하세요.",
            null),               //최종 성공
    REQUEST_INFO(2,
            "ID와 Web에서 발급받은 임시 키를 입력하세요.('/'로 구분)\n" +
                    "Ex) honggildong/q1w2e3",
            SUCCESS),           //"아이디 등록"을 요청 메시지로 받았을때
    NO_BASE(1,
            "ID를 등록하려면 아래 버튼을 누르거나 '아이디 등록'이라고 말씀해주세요.",
            REQUEST_INFO);       //ID를 등록하려면 '아이디 등록'...을 전송해야하는 단계, 아무것도 안한 단계

    private final int step;
    private final String message;
    private final ChatBotStep nextStep;

    public static ChatBotStep valueOf(int step) {
        switch (step) {
            case 1: return NO_BASE;
            case 2: return REQUEST_INFO;
            case 3: return SUCCESS;
            default: throw new AssertionError("Unkown step : " + step);
        }
    }
}
