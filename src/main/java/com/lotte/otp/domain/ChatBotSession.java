package com.lotte.otp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Created by choi on 2018. 2. 1. PM 3:51.
 */
@Component
public class ChatBotSession {

    @Autowired
    private HttpSession httpSession;

    public ChatBotStep getHttpSession(String kakaoUserKey) {
        return (ChatBotStep) httpSession.getAttribute(kakaoUserKey);
    }

    public void setHttpSession(String kakaoUserKey, ChatBotStep chatBotStep) {
        this.httpSession.setAttribute(kakaoUserKey, chatBotStep);
        this.httpSession.setMaxInactiveInterval(10 * 60);   //10분의 세션 유지 시간
    }

    public void nextStep(String kakaoUserKey) {
        if (httpSession.getAttribute(kakaoUserKey) == null) {
            setHttpSession(kakaoUserKey, ChatBotStep.NO_BASE);
        }
        ChatBotStep nextStep = ((ChatBotStep) httpSession.getAttribute(kakaoUserKey)).getNextStep();
        if (nextStep == null) {
            throw new IllegalStateException("다음 뎁스가 없습니다.");
        }
        setHttpSession(kakaoUserKey, nextStep);
    }
}
