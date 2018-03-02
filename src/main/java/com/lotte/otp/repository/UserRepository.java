package com.lotte.otp.repository;

import com.lotte.otp.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by choi on 2018. 3. 2. PM 2:25.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(@Param("id") String id);

}
