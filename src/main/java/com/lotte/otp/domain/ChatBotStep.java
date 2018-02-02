package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by choi on 2018. 2. 2. PM 3:59.
 */
@Getter
@AllArgsConstructor
public enum ChatBotStep {
    SUCCESS_REGIST(3, null),            //
    REQUEST_INFO(2, SUCCESS_REGIST),    //"아이디 등록"을 요청 메시지로 받았을때
    NO_CONTENT(1, REQUEST_INFO);        //ID를 등록하려면 '아이디 등록'...을 전송해야하는 단계, 아무것도 안한 단계

    private final int step;
    private final ChatBotStep nextStep;

    public static ChatBotStep valueOf(int step) {
        switch (step) {
            case 1: return NO_CONTENT;
            case 2: return REQUEST_INFO;
            case 3: return SUCCESS_REGIST;
            default: throw new AssertionError("Unkown step : " + step);
        }
    }
}
