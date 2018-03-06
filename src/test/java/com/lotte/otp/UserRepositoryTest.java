package com.lotte.otp;

import com.lotte.otp.domain.User;
import com.lotte.otp.repository.UserRepository;
import com.lotte.otp.util.DateUtils;
import com.lotte.otp.util.SecurityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by choi on 2018. 3. 2. PM 2:39.
 */
public class UserRepositoryTest extends OtpApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findById() {
        User user = userRepository.findById("test22");
        logger.info(String.valueOf(user));
        assertThat(user, is(notNullValue(User.class)));
    }

    @Test
    public void addUser() {
        User beforeSaveUser = new User(
                "testId",
                SecurityUtils.passwordEncoder("123123")
        );
        User afterSaveUser = userRepository.save(beforeSaveUser);
        logger.info(String.valueOf(afterSaveUser));
        assertThat(afterSaveUser.getId(), equalTo(beforeSaveUser.getId()));
    }

    @Test
    public void datetime() {
        logger.info(DateUtils.formatDateTime(DateUtils.currentDateTime(LocalDateTime.now())));
    }

    @Test
    public void keyTimeout() {
        logger.info(String.valueOf(SecurityUtils.isTimeoutKey(DateUtils.currentDateTime(LocalDateTime.now().minusSeconds(10)), 1)));
        logger.info(String.valueOf(SecurityUtils.isTimeoutKey(DateUtils.currentDateTime(LocalDateTime.now().minusMinutes(2)), 1)));
    }
}
