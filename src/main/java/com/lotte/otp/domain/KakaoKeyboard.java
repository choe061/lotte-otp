package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choi on 2018. 1. 29. PM 2:56.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoKeyboard {
    private String type;
    private String[] buttons;
}
