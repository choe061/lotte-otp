package com.lotte.otp;

import com.lotte.otp.domain.User;
import com.lotte.otp.domain.UserConnectionHistory;
import com.lotte.otp.repository.UserConnectionHistoryRepository;
import com.lotte.otp.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by choi on 2018. 3. 3. PM 6:03.
 */
public class UserConnectionHistoryRepositoryTest extends OtpApplicationTests {

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
//        logger.info(String.valueOf(history));
        assertThat(history, is(notNullValue(UserConnectionHistory.class)));
    }

    @Test
    public void findTopByKakaoUserKey() {
        UserConnectionHistory history = userConnectionHistoryRepository.findTopByKakaoUserKey("dYq1wL6LmnnR");
//        logger.info(String.valueOf(history));
        assertThat(history, is(notNullValue(UserConnectionHistory.class)));
    }
}
