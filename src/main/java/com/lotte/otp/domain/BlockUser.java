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
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BLOCK_USER")
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
    @Column(name = "blocked_date", nullable = false)
    private Date blocked_date;

    public BlockUser(String userId, String userIp) {
        this.userId = userId;
        this.userIp = userIp;
    }
}
