package com.lotte.otp;

import com.lotte.otp.domain.BlockUser;
import com.lotte.otp.repository.BlockUserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by choi on 2018. 3. 1. PM 8:43.
 */
public class BlockUserRepositoryTest extends OtpApplicationTests {

    @Autowired
    private BlockUserRepository blockUserRepository;

    @Test
    public void successGetBlockUser() {
        BlockUser blockUser = blockUserRepository.findOne(1);
//        logger.info(String.valueOf(blockUser));
        assertThat(blockUser, is(notNullValue(BlockUser.class)));
    }

    @Test
    public void failueGetBlockUser() {
        BlockUser blockUser = blockUserRepository.findOne(10000);
//        logger.info(String.valueOf(blockUser));
        assertThat(blockUser, is(nullValue()));
    }

    @Test
    public void successFindByUserId() {
        BlockUser blockUser = blockUserRepository.findByUserId("tester");
//        logger.info(String.valueOf(blockUser));
        assertThat(blockUser, is(notNullValue(BlockUser.class)));
    }

    @Test
    public void failueFindByUserId() {
        BlockUser blockUser = blockUserRepository.findByUserId("choe061");
//        logger.info(String.valueOf(blockUser));
        assertThat(blockUser, is(nullValue()));
    }

    @Test
    public void successFindByUserIdAndUserIp() {
        BlockUser blockUser = blockUserRepository.findByUserIdAndUserIp("tester", "123.1.0.123");
//        logger.info(String.valueOf(blockUser));
        assertThat(blockUser, is(notNullValue(BlockUser.class)));
    }

    @Test
    public void failueFindByUserIdAndUserIp() {
        BlockUser blockUser = blockUserRepository.findByUserIdAndUserIp("tester", "0.1.0.123");
//        logger.info(String.valueOf(blockUser));
        assertThat(blockUser, is(nullValue()));
    }

    @Test
    public void getBlockUsers() {
        List<BlockUser> blockUsers = blockUserRepository.findAll();
//        logger.info(String.valueOf(blockUsers));
        assertThat(blockUsers, is(notNullValue()));
    }

    @Test
    public void addBlockUser() {
        BlockUser blockUser = blockUserRepository.save(new BlockUser("asdasd123", "127.0.0.1"));
        assertThat(blockUser, is(notNullValue(BlockUser.class)));
    }
}
