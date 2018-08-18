package com.lotte.otp.service;

import com.lotte.otp.domain.*;
import com.lotte.otp.domain.type.Button;
import com.lotte.otp.domain.type.ChatBotStep;
import com.lotte.otp.domain.type.UserAuthStatus;
import com.lotte.otp.exception.KeyTimeoutException;
import com.lotte.otp.exception.LotteServiceException;
import com.lotte.otp.exception.UnAuthorizedUserException;
import com.lotte.otp.repository.User2NdAuthRepository;
import com.lotte.otp.repository.UserConnectionHistoryRepository;
import com.lotte.otp.repository.UserRepository;
import com.lotte.otp.service.strategy.button.ButtonClickEventFactory;
import com.lotte.otp.service.strategy.button.ButtonClickEventStrategy;
import com.lotte.otp.util.ChattingText;
import com.lotte.otp.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Created by choi on 2018. 2. 2. PM 2:04.
 */
@Transactional
@Service
public class PlusFriendService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private User2NdAuthRepository user2NdAuthRepository;
    @Autowired
    private UserConnectionHistoryRepository userConnectionHistoryRepository;
    @Autowired
    private ChatRedisService chatRedisService;

    @Autowired
    private ButtonClickEventFactory buttonClickEventFactory;

    public KakaoResponseMessage getMessage(KakaoRequestMessage message) {
        // 연동이 되어있는 회원의 경우
        if (isUser2NdAuthConnection(message.getUser_key())) {
            return new KakaoResponseMessage(
                    new KakaoMessage(getButtonMessage(message)),
                    ChattingText.DEFAULT_KEYBOARD
            );
        }

        //TODO Refactoring : chaing of responsibility 패턴 형식으로 ChatBotStep 리펙토링
        return null;
    }

    /**
     * 웹과 연동 플로우
     * 1. 먼저 연동이 되어 있는지 확인 - 컨트롤러단에서 확인하여 chat()메소드와 분기 호출
     * 2. 안되어있으면 키발급 프로세스 시작
     *
     * @return ChatBotStep.SUCCESS.getMessage() or 실패 메시지
     */
    public String connectWebService(KakaoRequestMessage message) {
        UserConnection userConnection = tokenizeText(message.getContent());
        if (userConnection == null) {
            logger.info("[플친 연동 Service] OTP 연동에 실패했습니다. 에러 내용 => 토크나이징 실패");
            return "잘못된 입력입니다.\n" + ChatBotStep.REQUEST_INFO.getMessage();
        }

        try {
            vertifyUserConnection(userConnection);
            User2NdAuth user2NdAuth = new User2NdAuth(
                    userRepository.findById(userConnection.getId()).getUuid(),
                    SecurityUtils.generateSecretKey(),
                    message.getUser_key()
            );

            logger.info("[플친 연동 Service] Auth => " + user2NdAuth.getUuid() + ", " + user2NdAuth.getKakaoUserKey() + ", " + user2NdAuth.getSecretKey());
            user2NdAuthRepository.save(user2NdAuth);
            return ChatBotStep.SUCCESS.getMessage();
        } catch (KeyTimeoutException | UnAuthorizedUserException e) {
            logger.info("[플친 연동 Service] OTP 연동에 실패했습니다. 에러 내용 => " + e.getMessage());
            String responseMessage = "";
            if (e.getErrorCode() == 401) {          //UnAuthorizedUserException
                responseMessage = "잘못된 정보 입력으로 OTP 연동에 실패했습니다. 사용자 정보를 다시 입력해주세요.";
            } else if (e.getErrorCode() == 408) {   //KeyTimeoutException
                responseMessage = "임시 키의 제한시간이 만료되어 OTP 연동에 실패했습니다. 키를 다시 발급받으세요.\n(임시 키 만료시간은 5분입니다.)";
            }
            return responseMessage;
        }
    }

    /**
     * button 클릭에 따라 다른 이벤트가 작동
     * ButtonClickEventFactory 에서 각 버튼에 따른 strategy 인스턴스를 제공
     * 연동된 회원은 채팅 진행
     * 1. 발급 or 재발급
     * 2. 로그인 내역 확인
     * @param message
     * @return
     */
    private String getButtonMessage(KakaoRequestMessage message) {
        try {
            ButtonClickEventStrategy strategy = buttonClickEventFactory.createStrategy(Button.findByButtonTitle(message.getContent()));
            return strategy.makeMessage(message);
        } catch (LotteServiceException e) {
            e.printStackTrace();
            return ChattingText.NO_MATCHING[(int)(Math.random() * 10) % ChattingText.NO_MATCHING.length];
        }
    }

    private UserConnection tokenizeText(String text) {
        UserConnection tokens = null;
        try {
            tokens = SecurityUtils.tokenizeText(text);
        } catch (Exception e) {
            return null;
        }
        return tokens;
    }

    /**
     * 1) 유저 + tempKey + 키 만료일시 => 모두 만족, OK
     * 2) temp_key가 일치하지 않는 경우 => UnAuthorizedUserException
     * 3) 만료일시가 지난 경우 => KeyTimeoutException
     * @param userConnectionQueue
     * @return
     */
    private void vertifyUserConnection(UserConnection userConnectionQueue) {
        int tempKey = chatRedisService.getTempKey(userConnectionQueue.getId());
        if (tempKey != userConnectionQueue.getTemp_key()) {
            throw new UnAuthorizedUserException();
        }
    }

    private boolean isUser2NdAuthConnection(String kakaoUserKey) {
        User2NdAuth user2NdAuth = user2NdAuthRepository.findByKakaoUserKey(kakaoUserKey);
        return Objects.nonNull(user2NdAuth);
    }

}
