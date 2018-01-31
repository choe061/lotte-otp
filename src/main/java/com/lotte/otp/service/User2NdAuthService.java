package com.lotte.otp.service;

import com.lotte.otp.repository.User2NdAuthMapper;
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
    private static final int EXIST_USER_AUTH = 1;

    public boolean isUser2NdAuth(String id) {
        int user_auth = user2NdAuthMapper.findUser2NdAuth(id);
        if (user_auth >= EXIST_USER_AUTH) {
            return true;
        }
        return false;
    }
}
