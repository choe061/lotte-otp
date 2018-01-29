package com.lotte.otp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by choi on 2018. 1. 29. PM 1:15.
 */
@RestController
public class MainController {

    @RequestMapping("/sign-up")
    public ModelAndView signUp() {
        return new ModelAndView("sign-up");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}
