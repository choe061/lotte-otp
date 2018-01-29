package com.lotte.otp.service;

import com.lotte.otp.domain.UserVO;
import com.lotte.otp.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by choi on 2018. 1. 26. PM 4:00.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 중복된 ID가 있으면 true 리턴
     * @param userId
     * @return boolean
     */
    public boolean duplicateUserId(String userId) {
        try {
            int id = userMapper.duplicateUserId(userId);
        } catch (RuntimeException e) {
            return true;
        }
        return false;
    }

    public boolean createUser(UserVO user) {

        return true;
    }

}
