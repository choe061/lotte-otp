package com.lotte.otp.service;

import com.lotte.otp.domain.User;
import com.lotte.otp.domain.UserAuthStatus;
import com.lotte.otp.domain.UserConnectionHistoryVO;
import com.lotte.otp.domain.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
public interface UserService {

    @Transactional(readOnly = true)
    boolean duplicateUserId(String userId);

    boolean createUser(User user);

    @Transactional(readOnly = true)
    UserAuthStatus login(String id, String pw);

    void insertConnectionHistory(boolean result, String id, UserConnectionHistoryVO history);

    @Transactional(readOnly = true)
    ArrayList<UserConnectionHistoryVO> getAllConnectionHistoryWithId(String id);

    @Transactional(readOnly = true)
    UserConnectionHistoryVO getConnectionHistoryWithId(String id);

}
