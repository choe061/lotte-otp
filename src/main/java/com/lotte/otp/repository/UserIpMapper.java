package com.lotte.otp.repository;

import com.lotte.otp.domain.UserIPVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by choi on 2018. 2. 5. PM 5:03.
 */
@Repository
public interface UserIpMapper {

    void insertConnectionHistory(@Param("user-ip") UserIPVO userIP);

    ArrayList<UserIPVO> getConnectionHistory(@Param("kakao_user_key") String kakao_user_key);

    ArrayList<UserIPVO> getAllConnectionHistory(@Param("kakao_user_key") String kakao_user_key);

}
