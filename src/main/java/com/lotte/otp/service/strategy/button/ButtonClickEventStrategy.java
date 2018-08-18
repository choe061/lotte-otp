package com.lotte.otp.service.strategy.button;

import com.lotte.otp.domain.KakaoRequestMessage;
import com.lotte.otp.domain.type.Button;
import com.lotte.otp.util.ChattingText;

/**
 * Created by choi on 2018. 8. 18. AM 1:21.
 */
public interface ButtonClickEventStrategy {

    default String makeMessage(KakaoRequestMessage message) {
        return ChattingText.NO_MATCHING[(int)(Math.random() * 10) % ChattingText.NO_MATCHING.length];
    }

    boolean isThisButton(Button button);

}
