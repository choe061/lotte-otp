package com.lotte.otp.repository;

import com.lotte.otp.domain.UserConnectionQueueVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by choi on 2018. 2. 2. PM 2:19.
 */
@Repository
public interface UserConnectionQueueMapper {

    UserConnectionQueueVO getUserConnection(@Param("id") String id);

    void insertTempKey(@Param("id") String id, @Param("temp_key") int temp_key);    //발급 요청 시 추가

    void deleteTempKey(@Param("id") String id); //삭제 후 추가 or 인증 완료 후 삭제
}
