package com.lotte.otp.service;

import com.lotte.otp.domain.BlockUserVO;
import com.lotte.otp.domain.UserAuthStatus;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface User2NdAuthService {

    @Transactional(readOnly = true)
    UserAuthStatus isUser2NdAuthWithID(String id);

    @Transactional(readOnly = true)
    UserAuthStatus isUser2NdAuthWithUserKey(String kakaoUserKey);

    @Transactional(readOnly = true)
    boolean authenticateOtp(String id, String otp);

    void blockUserIp(BlockUserVO blockUser);

    @Transactional(readOnly = true)
    boolean getBlockUserIp(String id, String ip);

}
