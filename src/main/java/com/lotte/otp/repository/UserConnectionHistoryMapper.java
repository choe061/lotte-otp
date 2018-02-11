package com.lotte.otp.repository;

import com.lotte.otp.domain.UserConnectionHistoryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by choi on 2018. 2. 5. PM 5:03.
 */
@Repository
public interface UserConnectionHistoryMapper {

    void insertConnectionHistory(@Param("connection_hisoty") UserConnectionHistoryVO userConnectionHistory);

    UserConnectionHistoryVO getConnectionHistory(@Param("kakao_user_key") String kakao_user_key);

    ArrayList<UserConnectionHistoryVO> getAllConnectionHistory(@Param("kakao_user_key") String kakao_user_key);

    UserConnectionHistoryVO getConnectionHistoryWithId(@Param("id") String id);

    ArrayList<UserConnectionHistoryVO> getAllConnectionHistoryWithId(@Param("id") String id);

}
