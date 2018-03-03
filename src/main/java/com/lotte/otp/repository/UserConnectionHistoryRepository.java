package com.lotte.otp.repository;

import com.lotte.otp.domain.UserConnectionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by choi on 2018. 3. 3. PM 4:32.
 */
@Repository
public interface UserConnectionHistoryRepository extends JpaRepository<UserConnectionHistory, Integer> {

    @Query(value = "SELECT h.* " +
            "FROM USER_CONNECTION_HISTORY AS h JOIN USER_2ND_AUTH AS a " +
            "ON h.uuid = a.user_uuid\n" +
            "WHERE a.kakao_user_key=?1 " +
            "ORDER BY h.accessed_date DESC LIMIT 1",
            nativeQuery = true
    )
    UserConnectionHistory findTopByKakaoUserKey(@Param("kakao_user_key") String kakaoUserKey);

    @Query(value = "SELECT h.* " +
            "FROM USER_CONNECTION_HISTORY AS h JOIN USER AS u " +
            "ON h.uuid = u.uuid " +
            "WHERE u.id=?1 " +
            "ORDER BY h.accessed_date DESC LIMIT 1",
            nativeQuery = true
    )
    UserConnectionHistory findTopByID(@Param("id") String id);
}
