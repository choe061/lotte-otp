package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choi on 2018. 1. 30. PM 2:23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoMessageButtonVO {

    private String label;
    private String url;

}
