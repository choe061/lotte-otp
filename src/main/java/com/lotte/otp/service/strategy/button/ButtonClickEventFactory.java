package com.lotte.otp.service.strategy.button;

import com.lotte.otp.domain.type.Button;
import com.lotte.otp.exception.LotteServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by choi on 2018. 8. 18. AM 1:19.
 */
@Component
public class ButtonClickEventFactory {

    @Autowired
    private List<ButtonClickEventStrategy> strategyList;

    public ButtonClickEventStrategy createStrategy(Button button) {
        for (ButtonClickEventStrategy strategy : strategyList) {
            if (strategy.isThisButton(button)) {
                return strategy;
            }
        }
        throw new LotteServiceException(400, "현재 버튼은 이용할 수 없습니다.");
    }
}
