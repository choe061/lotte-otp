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
    private String created_at;

    private String pw2;

    public UserVO(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public UserVO(String id, String pw, String pw2) {
        this.id = id;
        this.pw = pw;
        this.pw2 = pw2;
    }

    public UserVO(int uuid, String id, String pw, String created_at) {
        this.uuid = uuid;
        this.id = id;
        this.pw = pw;
        this.created_at = created_at;
    }
}
