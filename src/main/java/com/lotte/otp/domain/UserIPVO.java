package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Created by choi on 2018. 1. 29. AM 9:57.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIPVO {
    private int ip_id;
    private int uuid;
    private String ip;
    private String os;
    private String browser;
    private Date accessed_at;
    private boolean success;
}
