package com.lotte.otp;

import com.lotte.otp.domain.User;
import com.lotte.otp.domain.User2NdAuth;
import com.lotte.otp.repository.User2NdAuthRepository;
import com.lotte.otp.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Created by choi on 2018. 3. 2. PM 5:14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtpApplication.class)
public class User2NdAuthRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(User2NdAuthRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private User2NdAuthRepository user2NdAuthRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void findByUser2NdAuthWithID() {
        User user = userRepository.findById("tester");
        User2NdAuth user2NdAuth = user.getUser2NdAuth();
        logger.info(String.valueOf(user));
        if (user2NdAuth == null) {
            logger.info("OTP 미연동 회원입니다");
        } else {
            logger.info(String.valueOf(user2NdAuth));
        }
    }

    @Test
    public void findByKakaoUserKey() {
        User2NdAuth user2NdAuth = user2NdAuthRepository.findByKakaoUserKey("dYq1wL6LmnnR");
        logger.info(String.valueOf(user2NdAuth));
    }

    @Test
    public void updateLastPublishedDate() {
        User2NdAuth user2NdAuth = user2NdAuthRepository.findByKakaoUserKey("dYq1wL6LmnnR");
        logger.info("변경 전 => " + user2NdAuth.getLastPublishedDate());
        user2NdAuth.setLastPublishedDate(new Date());
        user2NdAuth = user2NdAuthRepository.save(user2NdAuth);
        logger.info("변경 후 => " + user2NdAuth.getLastPublishedDate());
    }
}
