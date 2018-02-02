package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Created by choi on 2018. 2. 2. PM 1:51.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConnectionQueueVO {
    private int qid;
    private String id;
    private int temp_key;
    private Date published_at;

    public UserConnectionQueueVO(String id, int temp_key) {
        this.id = id;
        this.temp_key = temp_key;
    }
}
