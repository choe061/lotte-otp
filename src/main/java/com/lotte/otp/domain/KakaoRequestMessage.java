package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choi on 2018. 1. 29. PM 3:13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoRequestMessage {
    private String user_key;
    private String type;
    private String content;
}
