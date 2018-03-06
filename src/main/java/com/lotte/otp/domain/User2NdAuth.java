package com.lotte.otp.domain;

import com.lotte.otp.config.LocalDateTimePersistenceConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by choi on 2018. 3. 2. PM 4:47.
 */
@Entity
@Table(name = "USER_2ND_AUTH")
@Data
@ToString(exclude = "user")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User2NdAuth implements Serializable {
    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "auth_id", updatable = false, nullable = false, length = 11)
//    private int auth_id;

    @Id
    @Column(name = "user_uuid", insertable = false, updatable = false, nullable = false, length = 11)
    private int uuid;

    @Column(name = "secret_key", nullable = false, length = 255)
    private String secretKey;

    @Column(name = "kakao_user_key", length = 12)
    private String kakaoUserKey;

    @LastModifiedDate
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    @Column(name = "last_published_date")
    private LocalDateTime lastPublishedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public User2NdAuth(int uuid, String secretKey, String kakaoUserKey) {
        this.uuid = uuid;
        this.secretKey = secretKey;
        this.kakaoUserKey = kakaoUserKey;
    }
}
