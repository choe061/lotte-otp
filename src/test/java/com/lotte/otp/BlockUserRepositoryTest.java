package com.lotte.otp;

import com.lotte.otp.domain.BlockUser;
import com.lotte.otp.repository.BlockUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by choi on 2018. 3. 1. PM 8:43.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtpApplication.class)
public class BlockUserRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(BlockUserRepositoryTest.class);

    @Autowired
    private BlockUserRepository blockUserRepository;

    @Test
    public void getBlockUser() {
        BlockUser blockUser = blockUserRepository.findOne(1);
        logger.info(String.valueOf(blockUser));
    }

    @Test
    public void findByUserId() {
        BlockUser blockUser = blockUserRepository.findByUserId("tester");
        logger.info(String.valueOf(blockUser));
    }

    @Test
    public void findByUserIdAndUserIp() {
        BlockUser blockUser = blockUserRepository.findByUserIdAndUserIp("tester", "123.1.0.123");
        logger.info(String.valueOf(blockUser));
    }

    @Test
    public void getBlockUsers() {
        List<BlockUser> blockUsers = blockUserRepository.findAll();
        logger.info(String.valueOf(blockUsers));
    }

    @Test
    public void addBlockUser() {
        BlockUser blockUser = blockUserRepository.save(new BlockUser("qweqwe123", "127.0.0.1"));
        if (blockUser == null) {
            logger.info("Not ADD BLOCK_USER");
        } else {
            logger.info(String.valueOf(blockUser));
        }
    }
}
