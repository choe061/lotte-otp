package com.lotte.otp.service;

import com.lotte.otp.domain.ChatBotStep;
import com.lotte.otp.domain.KakaoRequestMessageVO;
import com.lotte.otp.domain.User2NdAuthVO;
import com.lotte.otp.domain.UserConnectionQueueVO;
import com.lotte.otp.exception.DuplicateUserIDException;
import com.lotte.otp.exception.TempKeyTimeOutException;
import com.lotte.otp.exception.UnAuthorizedUserException;
import com.lotte.otp.repository.User2NdAuthMapper;
import com.lotte.otp.repository.UserConnectionQueueMapper;
import com.lotte.otp.repository.UserMapper;
import com.lotte.otp.util.DateUtils;
import com.lotte.otp.util.PlusFriendResponse;
import com.lotte.otp.util.SecurityUtils;
import com.sun.org.apache.bcel.internal.classfile.Unknown;
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
        try {
            UserConnectionQueueVO userConnection = vertifyUserMatching(message.getContent());
            User2NdAuthVO user2NdAuth = new User2NdAuthVO(
                    userMapper.getUUID(userConnection.getId()),
                    SecurityUtils.generateSecretKey(),
                    message.getUser_key()
            );
            user2NdAuthMapper.insertUser2ndAuth(user2NdAuth);
            userConnectionQueueMapper.deleteTempKey(userConnection.getId());    //등록에 성공하면 커넥션큐테이블의 데이터를 삭제
            return ChatBotStep.SUCCESS.getMessage();
        } catch (UnAuthorizedUserException | TempKeyTimeOutException e) {
            logger.info("OTP 연동에 실패했습니다.\n" +
                    "에러 내용 => " + e.getMessage());
            return "OTP 연동에 실패했습니다.";
        }
    }

    /**
     * 1) 유저 + tempKey + 키 만료일시 => 모두 만족, OK
     * 2) 유저가 등록되지 않은 경우 =>
     * 3) temp_key가 일치하지 않는 경우 => UNAUTHORIZED
     * 4) 만료일시가 지난 경우 => TIME_OUT
     * @param text ex) choe061/123123
     * @return
     */
    private UserConnectionQueueVO vertifyUserMatching(String text) {
        UserConnectionQueueVO tokens = SecurityUtils.tokenizeText(text);
        UserConnectionQueueVO userConnection = userConnectionQueueMapper.getUserConnection(tokens.getId());

        if (userConnection.getTemp_key() != tokens.getTemp_key()) {
            throw new UnAuthorizedUserException();
        }

        long publishTime = DateUtils.convertStrToLongDate(userConnection.getPublished_at());
        Date now = new Date();
        long requestTime = now.getTime();
        logger.info("현재 시간 => " + now + ", 발급 시간 => " + userConnection.getPublished_at());
        logger.info("키 시간 차이 => " + (requestTime - publishTime));
        if (SecurityUtils.isTimeoutKey(publishTime, 5)) {
            throw new TempKeyTimeOutException();
        }
        return userConnection;
    }

}
