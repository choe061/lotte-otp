package com.lotte.otp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by choi on 2018. 2. 5. PM 10:04.
 */
@Component
public class CertificationInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CertificationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("first-certification") == null) {
            response.sendRedirect("/login");
            return false;
        } else {
            httpSession.setMaxInactiveInterval(5 * 60); //1차 로그인 세션 보관 시간은 5분
            return true;
        }
    }
}
