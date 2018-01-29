package com.lotte.otp.repository;

import com.lotte.otp.domain.User2NdAuthVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by choi on 2018. 1. 29. AM 10:08.
 */
@Repository
public interface User2NdAuthMapper {

    void insertUser2ndAuth(@Param("2nd_auth") User2NdAuthVO user2NdAuthVO);

    User2NdAuthVO getUser2ndAuth(@Param("uuid") int uuid);    //검증을하기 위해 secret_key, last_published_at 획득

}
