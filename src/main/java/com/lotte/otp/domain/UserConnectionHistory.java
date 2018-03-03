package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by choi on 2018. 3. 3. PM 4:16.
 */
@Entity
@Table(name = "USER_CONNECTION_HISTORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConnectionHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false, length = 11)
    private int id;

    @Transient
    private int uuid;

    @Column(name = "ip", updatable = false, nullable = false, length = 15)
    private String ip;

    @Column(name = "os", updatable = false, nullable = false, length = 10)
    private String os;

    @Column(name = "browser", updatable = false, nullable = false, length = 10)
    private String browser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "accessed_date", insertable=false, updatable=false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date accessed_date;

    @Column(name = "success", updatable = false, nullable = false)
    private boolean success;

    public UserConnectionHistory(String ip, String os, String browser) {
        this.ip = ip;
        this.os = os;
        this.browser = browser;
    }
}
