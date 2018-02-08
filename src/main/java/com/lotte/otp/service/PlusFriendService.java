package com.lotte.otp.service;

import com.lotte.otp.domain.ChatBotStep;
import com.lotte.otp.domain.KakaoRequestMessageVO;
import com.lotte.otp.domain.User2NdAuthVO;
import com.lotte.otp.domain.UserConnectionQueueVO;
import com.lotte.otp.exception.GenerateOtpException;
import com.lotte.otp.exception.KeyTimeoutException;
import com.lotte.otp.exception.UnAuthorizedUserException;
import com.lotte.otp.repository.User2NdAuthMapper;
import com.lotte.otp.repository.UserConnectionQueueMapper;
import com.lotte.otp.repository.UserMapper;
import com.lotte.otp.util.DateUtils;
import com.lotte.otp.util.OTP;
import com.lotte.otp.util.ChattingText;
import com.lotte.otp.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by choi on 2018. 2. 2. PM 2:04.
 */
@Service
@Transactional
public class PlusFriendService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private User2NdAuthMapper user2NdAuthMapper;
    @Autowired
    private UserConnectionQueueMapper userConnectionQueueMapper;

    /**
     * 연동된 회원은 채팅 진행
     * 1. 발급 or 재발급
     * 2. 로그인 내역 확인
     * @param message
     * @return
     */
    public String chat(KakaoRequestMessageVO message) {
        if (message.getContent().equals(ChattingText.REQUEST_OTP_BUTTON)) {
            String now = DateUtils.now();
            String secretKey = user2NdAuthMapper.getUserSecretKey(message.getUser_key());
            try {
                String otp = OTP.create(DateUtils.convertStrToLongDate(now), secretKey);
                user2NdAuthMapper.updateLastPublishTime(message.getUser_key(), now);
                return "OTP : " + otp +
                        "\n발급 일시 : " + now +
                        "\n만료 일시 : " + DateUtils.expireMin(now, 1);
            } catch (GenerateOtpException e) {
                logger.info("에러 내용 => " + e.getMessage());
                return e.getMessage();
            }
        } else if (message.getContent().equals(ChattingText.OTP_EXPIRATION_TIME_BUTTON)) {  //가장 최초 버튼 누를 경우 NullPointerException 처리
            String publishTime = user2NdAuthMapper.getLastPublishTime(message.getUser_key());
            if (SecurityUtils.isTimeoutKey(DateUtils.convertStrToLongDate(publishTime), 1)) {
                return "이전에 받은 OTP는 만료되었습니다. 새로운 OTP를 요청하세요.";
            }
            String expirationTime = DateUtils.expireMin(publishTime, 1);
            long remainSeconds = 0;
            try {
                remainSeconds = DateUtils.remainSeconds(expirationTime);
            } catch (KeyTimeoutException kte) {
                return "이전에 받은 OTP는 만료되었습니다. 새로운 OTP를 요청하세요.";
            }
            return "만료 일시 : " + expirationTime +
                    "\n현재 OTP는 " + remainSeconds + "초 남았습니다.";
        } else if (message.getContent().equals(ChattingText.LOGIN_HISTORY_BUTTON)) {
            //TODO USER_IP 테이블 데이터 확인
            return "개발해야함!";
        }
        return ChattingText.NO_MATCHING[(int)(Math.random() * 10) % 3];
    }

    /**
     * 웹과 연동 플로우
     * 1. 먼저 연동이 되어 있는지 확인 - 컨트롤러단에서 확인하여 chat()메소드와 분기 호출
     * 2. 안되어있으면 키발급 프로세스 시작
     *
     * @return ChatBotStep.SUCCESS.getMessage() or 실패 메시지
     */
    public String connectWebService(KakaoRequestMessageVO message) {
        UserConnectionQueueVO userConnection = tokenizeText(message.getContent());
        if (userConnection == null) {
            logger.info("OTP 연동에 실패했습니다.\n" +
                    "에러 내용 => 토크나이징 실패");
            return "잘못된 입력입니다.\n" +
                    ChatBotStep.REQUEST_INFO.getMessage();
        }

        try {
            userConnection = vertifyUserMatching(userConnection);
            User2NdAuthVO user2NdAuth = new User2NdAuthVO(
                    userMapper.getUUID(userConnection.getId()),
                    SecurityUtils.generateSecretKey(),
                    message.getUser_key()
            );
            logger.info("Auth => " + user2NdAuth.getUuid() + ", " + user2NdAuth.getKakao_user_key() + ", " + user2NdAuth.getSecret_key());
            user2NdAuthMapper.insertUser2ndAuth(user2NdAuth);
            userConnectionQueueMapper.deleteTempKey(userConnection.getId());    //등록에 성공하면 커넥션큐테이블의 데이터를 삭제
            return ChatBotStep.SUCCESS.getMessage();
        } catch (KeyTimeoutException | UnAuthorizedUserException e) {
            logger.info("OTP 연동에 실패했습니다.\n" +
                    "에러 내용 => " + e.getMessage());
            String responseMessage = "";
            if (e.getErrorCode() == 401) {          //UnAuthorizedUserException
                responseMessage = "잘못된 정보 입력으로 OTP 연동에 실패했습니다. 사용자 정보를 다시 입력해주세요.";
            } else if (e.getErrorCode() == 408) {   //KeyTimeoutException
                responseMessage = "임시 키의 제한시간이 만료되어 OTP 연동에 실패했습니다. 키를 다시 발급받으세요.\n" +
                        "(임시 키의 만료시간은 5분입니다.)";
            }
            return responseMessage;
        }
    }

    private UserConnectionQueueVO tokenizeText(String text) {
        UserConnectionQueueVO tokens = null;
        try {
            tokens = SecurityUtils.tokenizeText(text);
        } catch (Exception e) {
            return null;
        }
        return tokens;
    }

    /**
     * 1) 유저 + tempKey + 키 만료일시 => 모두 만족, OK
     * 2) 유저가 등록되지 않은 경우 =>
     * 3) temp_key가 일치하지 않는 경우 => UNAUTHORIZED
     * 4) 만료일시가 지난 경우 => TIME_OUT
     * @param userConnectionQueue
     * @return
     */
    private UserConnectionQueueVO vertifyUserMatching(UserConnectionQueueVO userConnectionQueue) {
        UserConnectionQueueVO userConnection = userConnectionQueueMapper.getUserConnection(userConnectionQueue.getId());

        if (userConnection.getTemp_key() != userConnectionQueue.getTemp_key()) {
            throw new UnAuthorizedUserException();
        }

        long publishTime = DateUtils.convertStrToLongDate(userConnection.getPublished_at());
        Date now = new Date();
        long requestTime = now.getTime();
        logger.info("현재 시간 => " + now + ", 발급 시간 => " + userConnection.getPublished_at());
        logger.info("키 시간 차이 => " + (requestTime - publishTime));
        if (SecurityUtils.isTimeoutKey(publishTime, 5)) {
            throw new KeyTimeoutException();
        }
        return userConnection;
    }

}
