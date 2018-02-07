package com.lotte.otp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by choi on 2018. 1. 29. PM 1:15.
 */
@RestController
public class PageController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/test")
    public ModelAndView test() {
        return new ModelAndView("history");
    }

    @RequestMapping("/sign-up")
    public ModelAndView signUp(HttpSession httpSession) {
        if (httpSession.getAttribute("first-certification") != null) {
            if (httpSession.getAttribute("otp-certification") != null) {
                return new ModelAndView("redirect:/main");
            }
        }
        return new ModelAndView("sign-up");
    }

    @RequestMapping("/login")
    public ModelAndView login(HttpSession httpSession) {
        if (httpSession.getAttribute("first-certification") != null) {
            if (httpSession.getAttribute("otp-certification") != null) {
                return new ModelAndView("redirect:/main");
            }
        }
        return new ModelAndView("login");
    }
}
