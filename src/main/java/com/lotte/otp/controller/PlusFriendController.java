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

import javax.servlet.http.HttpSession;

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
    private ChatBotSessionScope chatBotSessionScope;

    @RequestMapping(value = "/keyboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KakaoKeyboardVO getKeyboard() {
        return new KakaoKeyboardVO("buttons", new String[]{"OTP (재)발급", "로그인 내역 확인"});
    }

    /**
     * 5초 안에 응답을 전송해야함. 5초가 지나면 응답없음 메시지가 사용자에게 전송됨.
     *  -> 1차 로그인 후 바로 유저에게 메시지 전송이 불가능함.
     * @param message
     * @return
     */
    @RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KakaoResponseMessageVO message(@RequestBody KakaoRequestMessageVO message) {
        KakaoResponseMessageVO response = null;
        if (user2NdAuthService.isUser2NdAuthWithUserKey(message.getUser_key())) {
            String responseMessage = plusFriendService.chat(message);
            response = new KakaoResponseMessageVO(
                    new KakaoMessageVO(responseMessage),
                    new KakaoKeyboardVO("buttons", new String[]{"OTP (재)발급", "지난 로그인 내역 확인"})
            );
        } else {
            //세션 확인 -> 순서 정의



            if (message.getContent().equals("아이디 등록")) {
                //이미 등록되어 있는지 아닌지 확인
                response = new KakaoResponseMessageVO(new KakaoMessageVO(PlusFriendResponse.REQUEST_CONNECT));
                //String text = plusFriendService.connectWebService(message.getContent());
            } else {

                response = new KakaoResponseMessageVO(
                        new KakaoMessageVO("응답 : " + message.getContent(),
                                new KakaoMessageButtonVO("OTP 만료일시 확인", "www.naver.com")),
                        new KakaoKeyboardVO("buttons", new String[]{"OTP (재)발급", "로그인 내역 확인"})
                );
            }
        }
        logger.info("REQUEST Message : " + message.getUser_key() + ", " + message.getContent() + ", " + message.getType());
        return response;
    }

}
