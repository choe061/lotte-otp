package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by choi on 2018. 1. 29. AM 9:55.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User2NdAuthVO {
    private int auth_id;
    private int uuid;
    private String secret_key;
    private String kakao_user_key;
    private Date last_published_at;

    public User2NdAuthVO(int auth_id, int uuid, String secret_key) {
        this.auth_id = auth_id;
        this.uuid = uuid;
        this.secret_key = secret_key;
    }

    public User2NdAuthVO(int uuid, String secret_key, String kakao_user_key) {
        this.uuid = uuid;
        this.secret_key = secret_key;
        this.kakao_user_key = kakao_user_key;
    }
}
