package com.lotte.otp;

import com.lotte.otp.domain.User;
import com.lotte.otp.domain.UserConnectionHistory;
import com.lotte.otp.repository.UserConnectionHistoryRepository;
import com.lotte.otp.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by choi on 2018. 3. 3. PM 6:03.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtpApplication.class)
public class UserConnectionHistoryRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(UserConnectionHistoryRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConnectionHistoryRepository userConnectionHistoryRepository;

    @Test
    @Transactional
    public void findByAllUserConnectionHistoryWithID() {
        User user = userRepository.findById("choe061");
        logger.info(String.valueOf(user));
        List<UserConnectionHistory> userConnectionHistories = user.getUserConnectionHistories();
        logger.info(String.valueOf(userConnectionHistories));
    }

    @Test
    public void findByTopUserConnectionHistoryWithID() {
        UserConnectionHistory history = userConnectionHistoryRepository.findTopByID("choe061");
        logger.info(String.valueOf(history));
    }
}
