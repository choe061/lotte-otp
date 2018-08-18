package com.lotte.otp.service.strategy.button;

import com.lotte.otp.domain.KakaoRequestMessage;
import com.lotte.otp.domain.type.Button;
import org.springframework.stereotype.Component;

/**
 * Created by choi on 2018. 8. 18. AM 1:38.
 */
@Component
public class DefaultEventStrategy implements ButtonClickEventStrategy {

    @Override
    public String makeMessage(KakaoRequestMessage message) {
        return ButtonClickEventStrategy.super.makeMessage(message);
    }

    @Override
    public boolean isThisButton(Button button) {
        return Button.UNKNOWN.equals(button);
    }
}
