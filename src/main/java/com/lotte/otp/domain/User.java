package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by choi on 2018. 3. 2. PM 2:08.
 */
@Entity
@Table(name = "USER")
@Data
@ToString(exclude = {"user2NdAuth", "userConnectionHistories"})
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", updatable = false, nullable = false, length = 11)
    private int uuid;

    @Column(name = "id", updatable = false, nullable = false, length = 15)
    private String id;

    @Column(name = "pw", nullable = false, length = 60)
    private String pw;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", insertable=false, updatable=false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date created_date;

    @Transient
    private String pw2;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private User2NdAuth user2NdAuth;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid")
    private List<UserConnectionHistory> userConnectionHistories = new ArrayList<>();

    public User(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public User(String id, String pw, Date created_date) {
        this.id = id;
        this.pw = pw;
        this.created_date = created_date;
    }
}
