package com.lotte.otp.service;

import com.lotte.otp.domain.KakaoRequestMessageVO;
import com.lotte.otp.domain.UserConnectionQueueVO;
import com.lotte.otp.repository.User2NdAuthMapper;
import com.lotte.otp.repository.UserConnectionQueueMapper;
import com.lotte.otp.util.PlusFriendResponse;
import com.lotte.otp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by choi on 2018. 2. 2. PM 2:04.
 */
@Service
@Transactional
public class PlusFriendService {

    @Autowired
    private User2NdAuthMapper user2NdAuthMapper;
    @Autowired
    private UserConnectionQueueMapper userConnectionQueueMapper;

    /**
     * 연동된 회원은 채팅 진행
     * @param message
     * @return
     */
    public String chat(KakaoRequestMessageVO message) {

        return "";
    }

    /**
     * 웹과 연동 플로우
     * 1. 먼저 연동이 되어 있는지 확인 - 컨트롤러단에서 확인하여 chat()메소드와 분기 호출
     * 2. 안되어있으면 키발급 프로세스 시작
     *
     * @return ChatBotStep.SUCCESS.getMessage() or 실패 메시지
     */
    public String connectWebService(KakaoRequestMessageVO message) {
        
        return "";
    }

    /**
     * 1) 유저 + tempKey + 키 만료일시 => 모두 만족, OK
     * 2) 유저가 등록되지 않은 경우 =>
     * 3) temp_key가 일치하지 않는 경우 => UNAUTHORIZED
     * 4) 만료일시가 지난 경우 => TIME_OUT
     * @param text ex) choe061/123123
     * @return
     */
    private String vertifyUserMatching(String text) {
        UserConnectionQueueVO tokens = SecurityUtils.tokenizeText(text);

        UserConnectionQueueVO userConnection = userConnectionQueueMapper.getTempKey(tokens.getId());

        if (userConnection.getTemp_key() != tokens.getTemp_key()) {
            return PlusFriendResponse.UNAUTHORIZED;
        }

        long publishTime = userConnection.getPublished_at().getTime();
        if (SecurityUtils.isTimeoutTempKey(publishTime, 5)) {
            return PlusFriendResponse.TIME_OUT;
        }
        return PlusFriendResponse.OK;
    }

}
