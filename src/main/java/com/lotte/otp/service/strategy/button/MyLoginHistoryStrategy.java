package com.lotte.otp.service.strategy.button;

import com.lotte.otp.domain.KakaoRequestMessage;
import com.lotte.otp.domain.UserConnectionHistory;
import com.lotte.otp.domain.type.Button;
import com.lotte.otp.repository.UserConnectionHistoryRepository;
import com.lotte.otp.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by choi on 2018. 8. 18. AM 1:33.
 */
@Component
public class MyLoginHistoryStrategy implements ButtonClickEventStrategy {
    @Autowired
    private UserConnectionHistoryRepository userConnectionHistoryRepository;

    @Override
    public String makeMessage(KakaoRequestMessage message) {
        UserConnectionHistory history = userConnectionHistoryRepository.findTopByKakaoUserKey(message.getUser_key());
        String text = "[최근 로그인 내역]";
        text += "\n일시 : " + DateUtils.formatDateTime(history.getAccessed_date());
        text += "\n접속 환경 : " + history.getOs() + " " + history.getBrowser();
        text += "\nIP : " + history.getIp();
        return text;
    }

    @Override
    public boolean isThisButton(Button button) {
        return Button.LOGIN_HISTORY_BUTTON.equals(button);
    }
}
