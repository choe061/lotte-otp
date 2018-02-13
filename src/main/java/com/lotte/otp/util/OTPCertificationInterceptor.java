package com.lotte.otp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by choi on 2018. 2. 5. PM 11:02.
 */
@Component
public class OTPCertificationInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(OTPCertificationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("otp-certification") == null) {
            response.sendRedirect("/login");
            return false;
        } else {
//            logger.info("OTP Certification Session => " + httpSession.getAttribute("otp-certification"));
            httpSession.setMaxInactiveInterval(60 * 60);    //2차 로그인 세션 보관 시간은 1시간
            return true;
        }
    }
}
