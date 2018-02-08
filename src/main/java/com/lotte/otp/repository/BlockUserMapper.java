package com.lotte.otp.repository;

import com.lotte.otp.domain.BlockUserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by choi on 2018. 2. 8. PM 11:37.
 */
@Repository
public interface BlockUserMapper {

    void blockUserIp(@Param("user") BlockUserVO blockUser);

    int getBlockUserIp(@Param("id") String id, @Param("ip") String ip);

}
