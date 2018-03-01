package com.lotte.otp.repository;

import com.lotte.otp.domain.BlockUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by choi on 2018. 3. 1. PM 8:25.
 */
@Repository
public interface BlockUserRepository extends JpaRepository<BlockUser, Integer> {
    public BlockUser findByUserId(@Param("id") String userId);
    public BlockUser findByUserIdAndUserIp(@Param("id") String userId, @Param("ip") String userIp);
}
