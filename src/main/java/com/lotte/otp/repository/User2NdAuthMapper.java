package com.lotte.otp.repository;

import com.lotte.otp.domain.User2NdAuthVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by choi on 2018. 1. 29. AM 10:08.
 */
@Repository
public interface User2NdAuthMapper {

    int findUser2NdAuthWithID(@Param("id") String id);

    int findUser2NdAuthWithUserKey(@Param("kakao_user_key") String kakaoUserKey);

    String getUserSecretKey(@Param("kakao_user_key") String kakaoUserKey);

    User2NdAuthVO getUser2ndAuth(@Param("id") String id);   //검증을하기 위해 secret_key, last_published_at 획득

    void updateLastPublishTime(@Param("kakao_user_key") String kakaoUserKey,
                               @Param("last_published_at") String lastPublishTime);

    String getLastPublishTime(@Param("kakao_user_key") String kakaoUserKey);

    void insertUser2ndAuth(@Param("user_2nd_auth") User2NdAuthVO user2NdAuth);



}
