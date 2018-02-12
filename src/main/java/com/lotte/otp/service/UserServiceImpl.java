package com.lotte.otp.service;

import com.lotte.otp.domain.UserAuthStatus;
import com.lotte.otp.domain.UserConnectionHistoryVO;
import com.lotte.otp.domain.UserVO;
import com.lotte.otp.exception.DuplicateUserIDException;
import com.lotte.otp.repository.UserConnectionHistoryMapper;
import com.lotte.otp.repository.UserMapper;
import com.lotte.otp.util.DateUtils;
import com.lotte.otp.util.SecurityUtils;
import com.lotte.otp.util.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Created by choi on 2018. 1. 26. PM 4:00.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserConnectionHistoryMapper userConnectionHistoryMapper;
    private static final int EXIST_USER = 1;

    /**
     * 중복된 ID가 없으면 true 리턴
     * 있다면 DuplicateUserIDException 발생
     *
     * @param userId
     * @return boolean
     */
    @Override
    public boolean duplicateUserId(String userId) {
        int user = userMapper.duplicateUserId(userId);
        if (user == EXIST_USER) {
            throw new DuplicateUserIDException();
        }
        return true;
    }

    @Override
    public boolean createUser(UserVO user) {
        try {
            duplicateUserId(user.getId());
        } catch (DuplicateUserIDException e) {
            logger.info(e.getMessage());
            return false;
        }
        if (UserValidator.isValidationUserInfo(user)) {
            String encodingPW = SecurityUtils.passwordEncoder(user.getPw());
            logger.info("PW - " + encodingPW);
            user.setPw(encodingPW);
            user.setCreated_date(DateUtils.now());
            userMapper.createUser(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserAuthStatus login(String id, String pw) {
        UserVO user = userMapper.login(id);
        logger.info("User INFO - " + user.getId() + ", " + user.getPw());
        logger.info(String.valueOf(SecurityUtils.isValidationPassword(pw, user.getPw())));
        if (SecurityUtils.isValidationPassword(pw, user.getPw())) {
            return UserAuthStatus.OK;
        } else {
            return UserAuthStatus.UNAUTHORIZED;
        }
    }

    @Override
    public void insertConnectionHistory(boolean result, String id, UserConnectionHistoryVO history) {
        history.setSuccess(result);
        history.setUuid(userMapper.getUUID(id));
        userConnectionHistoryMapper.insertConnectionHistory(history);
    }

    @Override
    public ArrayList<UserConnectionHistoryVO> getAllConnectionHistoryWithId(String id) {
        return userConnectionHistoryMapper.getAllConnectionHistoryWithId(id);
    }

    @Override
    public UserConnectionHistoryVO getConnectionHistoryWithId(String id) {
        return userConnectionHistoryMapper.getConnectionHistoryWithId(id);
    }

}
