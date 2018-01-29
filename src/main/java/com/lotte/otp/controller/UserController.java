package com.lotte.otp.controller;

import com.lotte.otp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by choi on 2018. 1. 26. PM 2:57.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/home")
    public ModelAndView home() {
        return new ModelAndView("home");
    }
}
