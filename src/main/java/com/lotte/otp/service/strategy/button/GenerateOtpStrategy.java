package com.lotte.otp.service.strategy.button;

import com.lotte.otp.domain.KakaoRequestMessage;
import com.lotte.otp.domain.User2NdAuth;
import com.lotte.otp.domain.type.Button;
import com.lotte.otp.exception.GenerateOtpException;
import com.lotte.otp.repository.User2NdAuthRepository;
import com.lotte.otp.util.DateUtils;
import com.lotte.otp.util.OTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by choi on 2018. 8. 18. AM 1:24.
 */
@Component
public class GenerateOtpStrategy implements ButtonClickEventStrategy {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private User2NdAuthRepository user2NdAuthRepository;

    @Override
    public String makeMessage(KakaoRequestMessage message) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        User2NdAuth user2NdAuth = user2NdAuthRepository.findByKakaoUserKey(message.getUser_key());
        String secretKey = user2NdAuth.getSecretKey();

        try {
            String otp = OTP.create(DateUtils.convertToLong(currentDateTime), secretKey);
            user2NdAuth.setLastPublishedDate(currentDateTime);
            user2NdAuthRepository.save(user2NdAuth);
            return "OTP : " + otp +
                    "\n발급 일시 : " + DateUtils.formatDateTime(currentDateTime) +
                    "\n만료 일시 : " + DateUtils.formatDateTime(DateUtils.expireMin(currentDateTime, 1));
        } catch (GenerateOtpException e) {
            logger.info("에러 내용 => " + e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public boolean isThisButton(Button button) {
        return Button.REQUEST_OTP_BUTTON.equals(button);
    }
}
