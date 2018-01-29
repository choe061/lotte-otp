package com.lotte.otp.service;

import com.lotte.otp.domain.UserVO;
import com.lotte.otp.exception.DuplicateUserIDException;
import com.lotte.otp.repository.UserMapper;
import com.lotte.otp.util.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by choi on 2018. 1. 26. PM 4:00.
 */
@Service
@Transactional
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    private static final int NONE = -1;

    /**
     * 중복된 ID가 없으면 true 리턴
     * 있다면 DuplicateUserIDException 발생
     * @param userId
     * @return boolean
     */
    public boolean duplicateUserId(String userId) {
        try {
            userMapper.duplicateUserId(userId);
            return true;
        } catch (DataAccessException e) {
            logger.info("Database exception : " + e.getCause() + ". " + e.getMessage());
            throw new DuplicateUserIDException();
        }
    }

    public boolean createUser(UserVO user) {
        try {
            duplicateUserId(user.getId());
        } catch (DuplicateUserIDException e) {
            logger.info(e.getMessage());
            return false;
        }
        if (UserValidator.isValidationUserInfo(user)) {
            userMapper.createUser(user);
            return true;
        } else {
            return false;
        }
    }

}
