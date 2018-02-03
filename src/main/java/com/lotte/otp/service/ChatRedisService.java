package com.lotte.otp.service;

import com.lotte.otp.domain.ChatBotStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by choi on 2018. 2. 3. PM 5:44.
 */
@Service
public class ChatRedisService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    public ChatBotStep getStep(String id) {
        String step = (String) redisTemplate.opsForValue().get(id);
        logger.info("Redis Session 조회 1 => " + step);
        ChatBotStep chatBotStep = null;
        try {
            chatBotStep = ChatBotStep.valueOf(step);
        } catch (NullPointerException npe) {
            setStep(id);
            step = (String) redisTemplate.opsForValue().get(id);
            chatBotStep = ChatBotStep.valueOf(step);
        }
        logger.info("Redis Session 조회 2 => " + chatBotStep);
        return chatBotStep;
    }

    public void setStep(String id) {
        logger.info("Redis Session 초기화 => " + ChatBotStep.NO_BASE);
        redisTemplate.opsForValue().set(id, ChatBotStep.NO_BASE);
    }

    public void nextStep(String id) {
        ChatBotStep step = getStep(id);
        logger.info("Redis Session 저장 전 => " + step);
        ChatBotStep nextStep = step.getNextStep();
        redisTemplate.opsForValue().set(id, nextStep);
        logger.info("Redis Session 저장 후 => " + nextStep);
    }
}
