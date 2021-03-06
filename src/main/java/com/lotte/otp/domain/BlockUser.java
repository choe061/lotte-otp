package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by choi on 2018. 3. 1. PM 7:46.
 */
@Entity
@Table(name = "BLOCK_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "b_id", updatable = false, nullable = false, length = 11)
    private int id;

    @Column(name = "id", nullable = false, length = 15)
    private String userId;

    @Column(name = "ip", nullable = false, length = 15)
    private String userIp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "blocked_date", insertable=false, updatable=false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date blocked_date;

    @Transient
    private int count;

    public BlockUser(String userId, String userIp) {
        this.userId = userId;
        this.userIp = userIp;
    }

    public BlockUser(String userId, String userIp, int count) {
        this.userId = userId;
        this.userIp = userIp;
        this.count = count;
    }
}
