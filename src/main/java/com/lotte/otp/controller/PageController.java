package com.lotte.otp.controller;

import com.lotte.otp.domain.ChatBotStep;
import com.lotte.otp.service.ChatRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private HttpSession httpSession;
    @Autowired
    private ChatRedisService chatRedisService;

    @RequestMapping("/sign-up")
    public ModelAndView signUp() {
        return new ModelAndView("sign-up");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        String step = String.valueOf(chatRedisService.getStep("user"));
        if (step == null) {
            chatRedisService.setStep("user");
            logger.info(String.valueOf(chatRedisService.getStep("user")));
        } else if (ChatBotStep.valueOf(step) == ChatBotStep.NO_BASE) {
            chatRedisService.nextStep("user");
            logger.info(String.valueOf(chatRedisService.getStep("user")));
        } else if (ChatBotStep.valueOf(step) == ChatBotStep.REQUEST_INFO) {
            chatRedisService.nextStep("user");
            logger.info(String.valueOf(chatRedisService.getStep("user")));
        } else if (ChatBotStep.valueOf(step) == ChatBotStep.SUCCESS) {
            chatRedisService.nextStep("user");
            logger.info(String.valueOf(chatRedisService.getStep("user")));
        }
//        logger.info(httpSession.getId());
//        if (httpSession.getAttribute("user") == null) {
//            logger.info(String.valueOf(httpSession.getAttribute("user")));
//            httpSession.setAttribute("user", 1);
//        } else if ((int) httpSession.getAttribute("user") == 1) {
//            logger.info(String.valueOf(httpSession.getAttribute("user")));
//            httpSession.setAttribute("user", 2);
//        } else if ((int) httpSession.getAttribute("user") == 2) {
//            logger.info(String.valueOf(httpSession.getAttribute("user")));
//            httpSession.setAttribute("user", 3);
//        }
        return new ModelAndView("login");
    }
}
