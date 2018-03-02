package com.lotte.otp;

import com.lotte.otp.domain.User;
import com.lotte.otp.repository.UserRepository;
import com.lotte.otp.util.SecurityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by choi on 2018. 3. 2. PM 2:39.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtpApplication.class)
public class UserRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByid() {
        User user = userRepository.findById("choe061");
        logger.info(String.valueOf(user));
    }

    @Test
    public void addUser() {
        User userInfo = new User(
                "testId",
                SecurityUtils.passwordEncoder("123123")
        );
        User user = userRepository.save(userInfo);
        logger.info(String.valueOf(user));
    }
}
