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
public class ChatController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/keyboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KakaoKeyboardVO getKeyboard() {
        return new KakaoKeyboardVO("buttons", new String[]{"테스트"});
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public KakaoResponseMessageVO message(@RequestBody KakaoRequestMessageVO message) {
        logger.info("REQUEST Message : " + message.getUser_key() + ", " + message.getContent() + ", " + message.getType());
        return new KakaoResponseMessageVO(new KakaoMessageVO("응답 : " + message));
    }

}
