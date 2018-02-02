package com.lotte.otp.service;

import com.lotte.otp.repository.User2NdAuthMapper;
import com.lotte.otp.repository.UserConnectionQueueMapper;
import com.lotte.otp.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by choi on 2018. 1. 29. AM 10:12.
 */
@Service
@Transactional
public class User2NdAuthService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private User2NdAuthMapper user2NdAuthMapper;
    @Autowired
    private UserConnectionQueueMapper userConnectionQueueMapper;

    private static final int EXIST_USER_AUTH = 1;

    public boolean isUser2NdAuthWithID(String id) {
        int user = user2NdAuthMapper.findUser2NdAuthWithID(id);
        if (user >= EXIST_USER_AUTH) {
            return true;
        }
        return false;
    }

    public boolean isUser2NdAuthWithUserKey(String kakaoUserKey) {
        int user = user2NdAuthMapper.findUser2NdAuthWithID(kakaoUserKey);
        if (user >= EXIST_USER_AUTH) {
            return true;
        }
        return false;
    }

    public int distributeTempkey(String id) {
        int tempKey = SecurityUtils.distributeTempKey();
        userConnectionQueueMapper.deleteTempKey(id);
        userConnectionQueueMapper.insertTempKey(id, tempKey);
        return tempKey;
    }
}
