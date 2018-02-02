package com.lotte.otp.controller;

import com.lotte.otp.domain.*;
import com.lotte.otp.service.PlusFriendService;
import com.lotte.otp.service.User2NdAuthService;
import com.lotte.otp.util.PlusFriendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by choi on 2018. 1. 29. PM 2:31.
 */
@RestController
@RequestMapping("/kakaoApi")
public class PlusFriendController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private User2NdAuthService user2NdAuthService;
    @Autowired
    private PlusFriendService plusFriendService;
    @Autowired
    private ChatBotSession chatBotSession;

    @RequestMapping(value = "/keyboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KakaoKeyboardVO getKeyboard() {
        return new KakaoKeyboardVO("buttons", new String[]{"OTP (재)발급", "로그인 내역 확인"});
    }

    /**
     *
     * @param message
     * @return
     */
    @RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KakaoResponseMessageVO message(@RequestBody KakaoRequestMessageVO message) {
        logger.info("Session => " + chatBotSession.getHttpSession(message.getUser_key()));

        KakaoResponseMessageVO response = null;
        if (user2NdAuthService.isUser2NdAuthWithUserKey(message.getUser_key())) {
            String responseMessage = plusFriendService.chat(message);
            response = new KakaoResponseMessageVO(
                    new KakaoMessageVO(responseMessage),
                    new KakaoKeyboardVO("buttons", new String[]{"OTP (재)발급", "지난 로그인 내역 확인"})
            );
        } else {
            //세션 확인 -> 순서 정의
            if (chatBotSession.getHttpSession(message.getUser_key()) == null) {
                chatBotSession.setHttpSession(message.getUser_key(), ChatBotStep.NO_BASE);
                chatBotSession.nextStep(message.getUser_key());
                response = new KakaoResponseMessageVO(
                        new KakaoMessageVO(ChatBotStep.NO_BASE.getMessage()),
                        new KakaoKeyboardVO("buttons", new String[]{"ID 등록"})
                );
            } else if (chatBotSession.getHttpSession(message.getUser_key()) == ChatBotStep.REQUEST_INFO) {
                chatBotSession.nextStep(message.getUser_key());
                response = new KakaoResponseMessageVO(
                        new KakaoMessageVO(ChatBotStep.REQUEST_INFO.getMessage())
                );
            } else if (chatBotSession.getHttpSession(message.getUser_key()) == ChatBotStep.SUCCESS) {
                String responseMessage = plusFriendService.connectWebService(message);
                response = new KakaoResponseMessageVO(
                        new KakaoMessageVO(responseMessage),
                        new KakaoKeyboardVO("buttons", new String[]{"OTP (재)발급", "로그인 내역 확인"})
                );
            }
        }
        logger.info("REQUEST Message : " + message.getUser_key() + ", " + message.getContent() + ", " + message.getType());
        return response;
    }

}
