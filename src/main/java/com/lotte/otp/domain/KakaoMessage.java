package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choi on 2018. 1. 29. PM 3:25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoMessage {
    private String text;
    private KakaoMessageButton message_button;

    public KakaoMessage(String text) {
        this.text = text;
    }
}