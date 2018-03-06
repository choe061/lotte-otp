package com.lotte.otp.domain;

import com.lotte.otp.config.LocalDateTimePersistenceConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by choi on 2018. 3. 2. PM 2:08.
 */
@Entity
@Table(name = "USER")
@Data
@ToString(exclude = {"user2NdAuth", "userConnectionHistories", "pw", "pw2"})
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    @Column(name = "created_date", updatable=false)
    private LocalDateTime created_date;

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

    public User(String id, String pw, LocalDateTime created_date) {
        this.id = id;
        this.pw = pw;
        this.created_date = created_date;
    }
}
