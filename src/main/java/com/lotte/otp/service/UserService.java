package com.lotte.otp.service;

import com.lotte.otp.domain.User;
import com.lotte.otp.domain.UserAuthStatus;
import com.lotte.otp.domain.UserConnectionHistory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserService {

    @Transactional(readOnly = true)
    boolean duplicateUserId(String userId);

    boolean createUser(User user);

    @Transactional(readOnly = true)
    UserAuthStatus login(String id, String pw);

    void insertConnectionHistory(boolean result, String id, UserConnectionHistory history);

    @Transactional(readOnly = true)
    List<UserConnectionHistory> getAllConnectionHistoryWithId(String id);

    @Transactional(readOnly = true)
    UserConnectionHistory getConnectionHistoryWithId(String id);

}
