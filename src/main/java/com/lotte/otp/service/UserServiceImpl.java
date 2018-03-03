package com.lotte.otp.service;

import com.lotte.otp.domain.User;
import com.lotte.otp.domain.UserAuthStatus;
import com.lotte.otp.domain.UserConnectionHistory;
import com.lotte.otp.exception.DuplicateUserIDException;
import com.lotte.otp.repository.UserConnectionHistoryRepository;
import com.lotte.otp.repository.UserRepository;
import com.lotte.otp.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by choi on 2018. 1. 26. PM 4:00.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConnectionHistoryRepository userConnectionHistoryRepository;

    /**
     * 중복된 ID가 없으면 true 리턴
     * 있다면 DuplicateUserIDException 발생
     *
     * @param userId
     * @return boolean
     */
    @Override
    public boolean duplicateUserId(String userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            throw new DuplicateUserIDException();
        }
        return true;
    }

    @Override
    public boolean createUser(User user) {
        try {
            duplicateUserId(user.getId());
        } catch (DuplicateUserIDException e) {
            logger.info(e.getMessage());
            return false;
        }
        String encodingPW = SecurityUtils.passwordEncoder(user.getPw());
        logger.info("PW - " + encodingPW);
        user.setPw(encodingPW);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserAuthStatus login(String id, String pw) {
        User user = userRepository.findById(id);
        logger.info("User INFO - " + user.getId() + ", " + user.getPw());
        logger.info(String.valueOf(SecurityUtils.isValidationPassword(pw, user.getPw())));
        if (SecurityUtils.isValidationPassword(pw, user.getPw())) {
            return UserAuthStatus.OK;
        } else {
            return UserAuthStatus.UNAUTHORIZED;
        }
    }

    @Override
    public void insertConnectionHistory(boolean result, String id, UserConnectionHistory history) {
        int uuid = userRepository.findById(id).getUuid();
        history.setUuid(uuid);
        history.setSuccess(result);
        userConnectionHistoryRepository.save(history);
    }

    @Transactional
    @Override
    public List<UserConnectionHistory> getAllConnectionHistoryWithId(String id) {
        return userRepository.findById(id).getUserConnectionHistories();
    }

    @Override
    public UserConnectionHistory getConnectionHistoryWithId(String id) {
        return userConnectionHistoryRepository.findTopByID(id);
    }

}
