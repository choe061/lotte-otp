package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choi on 2018. 2. 2. PM 1:51.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConnection {

    private String id;
    private int temp_key;

}
