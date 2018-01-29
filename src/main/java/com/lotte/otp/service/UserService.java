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

    public boolean createUser(UserVO userVO) {

        return true;
    }

}
