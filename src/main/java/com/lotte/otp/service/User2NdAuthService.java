package com.lotte.otp.service;

import com.lotte.otp.domain.User2NdAuthVO;
import com.lotte.otp.repository.User2NdAuthMapper;
import com.lotte.otp.repository.UserConnectionQueueMapper;
import com.lotte.otp.util.DateUtils;
import com.lotte.otp.util.OTP;
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
        logger.info("Distribute key date => " + DateUtils.now());
        userConnectionQueueMapper.insertTempKey(id, tempKey, DateUtils.now());
        return tempKey;
    }

    public boolean authenticateOtp(String id, String otp) {
        User2NdAuthVO user2NdAuth = user2NdAuthMapper.getUser2ndAuth(id);
        boolean result = OTP.vertify(
                DateUtils.convertStrToLongDate(user2NdAuth.getLast_published_at()),
                user2NdAuth.getSecret_key(),
                otp
                );
        if (result && SecurityUtils.isTimeoutKey(DateUtils.convertStrToLongDate(user2NdAuth.getLast_published_at()), 1)) {
            return true;
        }
        return false;
    }
}
