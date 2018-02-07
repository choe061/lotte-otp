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
public class UserConnectionHistoryVO {
    private int id;
    private int uuid;
    private String ip;
    private String os;
    private String browser;
    private String accessed_at;
    private boolean success;

    public UserConnectionHistoryVO(String ip, String os, String browser, String accessed_at) {
        this.ip = ip;
        this.os = os;
        this.browser = browser;
        this.accessed_at = accessed_at;
    }

    public UserConnectionHistoryVO(String ip, String os, String browser, String accessed_at, boolean success) {
        this.ip = ip;
        this.os = os;
        this.browser = browser;
        this.accessed_at = accessed_at;
        this.success = success;
    }
}
