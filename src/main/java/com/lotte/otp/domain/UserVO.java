package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Created by choi on 2018. 1. 29. AM 9:53.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private int uuid;
    private String id;
    private String pw;
    private Date created_at;
}
