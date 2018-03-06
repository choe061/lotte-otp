package com.lotte.otp;

import com.lotte.otp.domain.User;
import com.lotte.otp.domain.User2NdAuth;
import com.lotte.otp.repository.User2NdAuthRepository;
import com.lotte.otp.repository.UserRepository;
import com.lotte.otp.util.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by choi on 2018. 3. 2. PM 5:14.
 */
public class User2NdAuthRepositoryTest extends OtpApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private User2NdAuthRepository user2NdAuthRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void failueFindByUser2NdAuthWithID() {
        User user = userRepository.findById("tester");
        User2NdAuth user2NdAuth = user.getUser2NdAuth();
//        logger.info(String.valueOf(user2NdAuth));
        assertThat(user2NdAuth, is(nullValue()));
    }

    @Test
    public void successFindByUser2NdAuthWithID() {
        User user = userRepository.findById("choe061");
        User2NdAuth user2NdAuth = user.getUser2NdAuth();
//        logger.info(String.valueOf(user2NdAuth));
        assertThat(user2NdAuth, is(notNullValue(User2NdAuth.class)));
    }

    @Test
    public void successFindByKakaoUserKey() {
        User2NdAuth user2NdAuth = user2NdAuthRepository.findByKakaoUserKey("dYq1wL6LmnnR");
//        logger.info(String.valueOf(user2NdAuth));
        assertThat(user2NdAuth, is(notNullValue(User2NdAuth.class)));
    }

    @Test
    public void failueFindByKakaoUserKey() {
        User2NdAuth user2NdAuth = user2NdAuthRepository.findByKakaoUserKey("0000");
//        logger.info(String.valueOf(user2NdAuth));
        assertThat(user2NdAuth, is(nullValue()));
    }

    @Test
    public void successUpdateLastPublishedDate() {
        User2NdAuth user2NdAuth = user2NdAuthRepository.findByKakaoUserKey("dYq1wL6LmnnR");
//        logger.info("변경 전 => " + user2NdAuth.getLastPublishedDate());
        LocalDateTime beforeDate = user2NdAuth.getLastPublishedDate();

        user2NdAuth.setLastPublishedDate(LocalDateTime.now());
        user2NdAuth = user2NdAuthRepository.save(user2NdAuth);
        LocalDateTime afterDate = user2NdAuth.getLastPublishedDate();

        assertThat(beforeDate, not(equalTo(afterDate)));
//        logger.info("변경 후 => " + user2NdAuth.getLastPublishedDate());
    }
}
