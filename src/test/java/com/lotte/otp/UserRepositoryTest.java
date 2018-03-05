package com.lotte.otp;

import com.lotte.otp.domain.User;
import com.lotte.otp.repository.UserRepository;
import com.lotte.otp.util.SecurityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        User user = userRepository.findById("choe061");
//        logger.info(String.valueOf(user));
        assertThat(user, is(notNullValue(User.class)));
    }

    @Test
    public void addUser() {
        User beforeSaveUser = new User(
                "testId",
                SecurityUtils.passwordEncoder("123123")
        );
        User afterSaveUser = userRepository.save(beforeSaveUser);
//        logger.info(String.valueOf(user));
        assertThat(afterSaveUser.getId(), equalTo(beforeSaveUser.getId()));
    }
}
