package com.lotte.otp.controller;

import com.lotte.otp.domain.KakaoKeyboardVO;
import com.lotte.otp.domain.KakaoMessageVO;
import com.lotte.otp.domain.KakaoRequestMessageVO;
import com.lotte.otp.domain.KakaoResponseMessageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @RequestMapping(value = "/keyboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KakaoKeyboardVO getKeyboard() {
        return new KakaoKeyboardVO("buttons", new String[]{"ID 등록"});
    }

    /**
     * 5초 안에 응답을 전송해야함. 5초가 지나면 응답없음 메시지가 사용자에게 전송됨.
     *  -> 1차 로그인 후 바로 유저에게 메시지 전송이 불가능함.
     * @param message
     * @return
     */
    @RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KakaoResponseMessageVO message(@RequestBody KakaoRequestMessageVO message) {
        KakaoResponseMessageVO response = new KakaoResponseMessageVO(
                new KakaoMessageVO("반사 : " + message.getContent()),
                new KakaoKeyboardVO("buttons", new String[]{"OTP (재)발급", "OTP 만료일시 확인", "로그인 내역 보러가기"})
        );
        logger.info("REQUEST Message : " + message.getUser_key() + ", " + message.getContent() + ", " + message.getType());
        return response;
    }

}