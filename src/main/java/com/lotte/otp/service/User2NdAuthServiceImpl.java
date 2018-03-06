package com.lotte.otp.service;

import com.lotte.otp.domain.BlockUser;
import com.lotte.otp.domain.User2NdAuth;
import com.lotte.otp.domain.UserAuthStatus;
import com.lotte.otp.repository.BlockUserRepository;
import com.lotte.otp.repository.User2NdAuthRepository;
import com.lotte.otp.repository.UserRepository;
import com.lotte.otp.util.DateUtils;
import com.lotte.otp.util.OTP;
import com.lotte.otp.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by choi on 2018. 1. 29. AM 10:12.
 */
@Service("user2NdAuthService")
public class User2NdAuthServiceImpl implements User2NdAuthService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BlockUserRepository blockUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private User2NdAuthRepository user2NdAuthRepository;

    @Override
    public UserAuthStatus isUser2NdAuthWithID(String id) {
        User2NdAuth user2NdAuth = userRepository.findById(id).getUser2NdAuth();
        if (user2NdAuth != null) {
            return UserAuthStatus.CONNECTION_OTP;
        }
        return UserAuthStatus.UNAUTHORIZED;
    }

    @Override
    public UserAuthStatus isUser2NdAuthWithUserKey(String kakaoUserKey) {
        User2NdAuth user2NdAuth = user2NdAuthRepository.findByKakaoUserKey(kakaoUserKey);
        if (user2NdAuth != null) {
            return UserAuthStatus.CONNECTION_OTP;
        }
        return UserAuthStatus.UNAUTHORIZED;
    }

    @Override
    public boolean authenticateOtp(String id, String otp) {
        User2NdAuth user2NdAuth = userRepository.findById(id).getUser2NdAuth();
        boolean result = OTP.vertify(
                DateUtils.convertToLong(user2NdAuth.getLastPublishedDate()),
                user2NdAuth.getSecretKey(),
                otp
        );
        if (result && !SecurityUtils.isTimeoutKey(user2NdAuth.getLastPublishedDate(), 1)) {
            return true;
        }
        return false;
    }

    @Override
    public void blockUserIp(BlockUser blockUser) {
        blockUserRepository.save(blockUser);
    }

    @Override
    public boolean getBlockUserIp(String id, String ip) {
        BlockUser blockUser = blockUserRepository.findByUserIdAndUserIp(id, ip);
        if (blockUser != null) {
            return true;
        }
        return false;
    }
}
