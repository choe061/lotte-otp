package com.lotte.otp.repository;

import com.lotte.otp.domain.User2NdAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by choi on 2018. 3. 2. PM 5:06.
 */
@Repository
public interface User2NdAuthRepository extends JpaRepository<User2NdAuth, Integer> {

    User2NdAuth findByKakaoUserKey(@Param("kakao_user_key") String kakaoUserKey);

//    @Modifying
//    @Query("UPDATE USER_2ND_AUTH SET last_published_date=?1 WHERE kakao_user_key=?2")
//    void setFixedLastPublishedDateFor(String lastPublishedDate, String kakaoUserKey);
}
