package com.lotte.otp.controller;

import com.lotte.otp.domain.*;
import com.lotte.otp.service.ChatRedisService;
import com.lotte.otp.service.PlusFriendService;
import com.lotte.otp.service.User2NdAuthService;
import com.lotte.otp.util.ChattingText;
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
    private ChatRedisService chatRedisService;

    @RequestMapping(value = "/keyboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KakaoKeyboardVO getKeyboard() {
        return new KakaoKeyboardVO("buttons", new String[]{
                ChattingText.REQUEST_OTP_BUTTON,
                ChattingText.OTP_EXPIRATION_TIME_BUTTON,
                ChattingText.LOGIN_HISTORY_BUTTON
        });
    }

    /**
     *
     * @param message
     * @return
     */
    @RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KakaoResponseMessageVO message(@RequestBody KakaoRequestMessageVO message) {
        KakaoResponseMessageVO response = null;
        if (user2NdAuthService.isUser2NdAuthWithUserKey(message.getUser_key())) {   //연동이 되어있는 회원의 경우
            String responseMessage = plusFriendService.chat(message);
            response = new KakaoResponseMessageVO(
                    new KakaoMessageVO(responseMessage),
                    new KakaoKeyboardVO("buttons", new String[]{
                            ChattingText.REQUEST_OTP_BUTTON,
                            ChattingText.OTP_EXPIRATION_TIME_BUTTON,
                            ChattingText.LOGIN_HISTORY_BUTTON
                    })
            );
        } else {        //연동이 되지 않은 회원의 경우
            String step = String.valueOf(chatRedisService.getStep(message.getUser_key()));
            logger.info(message.getUser_key() + " Session => " + step);
            if (ChatBotStep.valueOf(step) == ChatBotStep.NO_BASE
                    && (message.getContent().equals("아이디 등록") || message.getContent().equals(ChattingText.REGIST_ID_BUTTON))) {
                chatRedisService.nextStep(message.getUser_key());
                step = String.valueOf(chatRedisService.getStep(message.getUser_key()));
            }

            if (ChatBotStep.valueOf(step) == ChatBotStep.NO_BASE) {
                response = new KakaoResponseMessageVO(
                        new KakaoMessageVO(ChatBotStep.NO_BASE.getMessage()),
                        new KakaoKeyboardVO("buttons", new String[]{ChattingText.REGIST_ID_BUTTON})
                );
                if (message.getContent().equals("아이디 등록") || message.getContent().equals(ChattingText.REGIST_ID_BUTTON)) {
                    chatRedisService.nextStep(message.getUser_key());
                }
            } else if (ChatBotStep.valueOf(step) == ChatBotStep.REQUEST_INFO) {     //입력으로 "ID 등록"이 들어옴
                response = new KakaoResponseMessageVO(
                        new KakaoMessageVO(ChatBotStep.REQUEST_INFO.getMessage())
                );
                chatRedisService.nextStep(message.getUser_key());
            } else if (ChatBotStep.valueOf(step) == ChatBotStep.SUCCESS) {          //입력으로 ID/temp_key가 들어옴
                String responseMessage = plusFriendService.connectWebService(message);
                if (responseMessage.equals(ChatBotStep.SUCCESS.getMessage())) {
                    response = new KakaoResponseMessageVO(
                            new KakaoMessageVO(responseMessage),
                            new KakaoKeyboardVO("buttons", new String[]{
                                    ChattingText.REQUEST_OTP_BUTTON,
                                    ChattingText.OTP_EXPIRATION_TIME_BUTTON,
                                    ChattingText.LOGIN_HISTORY_BUTTON})
                    );
                } else {    //Exception이 발생한 경우
                    response = new KakaoResponseMessageVO(new KakaoMessageVO(responseMessage));
                }
            }
        }
        logger.info("REQUEST Message : " + message.getUser_key() + ", " + message.getContent() + ", " + message.getType());
        return response;
    }

}
