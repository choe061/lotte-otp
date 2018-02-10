package com.lotte.otp.service;

import com.lotte.otp.domain.ChatBotStep;
import com.lotte.otp.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by choi on 2018. 2. 3. PM 5:44.
 */
@Service
public class ChatRedisService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String TEMP_KEY = "_tempkey";

    @Autowired
    private RedisTemplate redisTemplate;

    public ChatBotStep getStep(String id) {
        String step = (String) redisTemplate.opsForValue().get(id);
        ChatBotStep chatBotStep = null;
        try {
            chatBotStep = ChatBotStep.valueOf(step);
        } catch (NullPointerException npe) {
            initStep(id);
            step = (String) redisTemplate.opsForValue().get(id);
            chatBotStep = ChatBotStep.valueOf(step);
        }
        logger.info("[ChatRedisService] Redis Session 조회 => " + chatBotStep);
        return chatBotStep;
    }

    public void setStep(String id, ChatBotStep step) {
        redisTemplate.opsForValue().set(id, step, 10, TimeUnit.MINUTES);
    }

    public void initStep(String id) {
        redisTemplate.opsForValue().set(id, ChatBotStep.NO_BASE, 10, TimeUnit.MINUTES);
    }

    public void nextStep(String id) {
        ChatBotStep step = getStep(id);
        ChatBotStep nextStep = step.getNextStep();
        redisTemplate.opsForValue().set(id, nextStep, 10, TimeUnit.MINUTES);
    }

    public int getTempKey(String id) {
        try {
            return (int) redisTemplate.opsForValue().get(id+TEMP_KEY);
        } catch (NullPointerException npe) {
            createTempKey(id);
            return (int) redisTemplate.opsForValue().get(id+TEMP_KEY);
        }
    }

    public void createTempKey(String id) {
        int tempKey = SecurityUtils.distributeTempKey();
        redisTemplate.opsForValue().set(id+TEMP_KEY, tempKey, 5, TimeUnit.MINUTES);
    }
}
