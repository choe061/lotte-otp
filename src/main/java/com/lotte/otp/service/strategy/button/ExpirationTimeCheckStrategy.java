package com.lotte.otp.service.strategy.button;

import com.lotte.otp.domain.KakaoRequestMessage;
import com.lotte.otp.domain.type.Button;
import com.lotte.otp.exception.KeyTimeoutException;
import com.lotte.otp.repository.User2NdAuthRepository;
import com.lotte.otp.util.DateUtils;
import com.lotte.otp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by choi on 2018. 8. 18. AM 1:31.
 */
@Component
public class ExpirationTimeCheckStrategy implements ButtonClickEventStrategy {
    @Autowired
    private User2NdAuthRepository user2NdAuthRepository;

    @Override
    public String makeMessage(KakaoRequestMessage message) {
        LocalDateTime publishedDateTime
                = user2NdAuthRepository.findByKakaoUserKey(message.getUser_key()).getLastPublishedDate();

        if (SecurityUtils.isTimeoutKey(publishedDateTime, 1)) {
            return "이전에 받은 OTP는 만료되었습니다. 새로운 OTP를 요청하세요.";
        }

        try {
            LocalDateTime expirationTime = DateUtils.expireMin(publishedDateTime, 1);
            int remainSeconds = DateUtils.remainSeconds(expirationTime);
            return "만료 일시 : " + DateUtils.formatDateTime(expirationTime) + "\n현재 OTP는 " + remainSeconds + "초 남았습니다.";
        } catch (KeyTimeoutException kte) {
            return "이전에 받은 OTP는 만료되었습니다. 새로운 OTP를 요청하세요.";
        }
    }

    @Override
    public boolean isThisButton(Button button) {
        return Button.OTP_EXPIRATION_TIME_BUTTON.equals(button);
    }
}
