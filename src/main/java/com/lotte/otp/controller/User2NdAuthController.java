package com.lotte.otp.controller;

import com.lotte.otp.domain.UserConnectionHistoryVO;
import com.lotte.otp.service.User2NdAuthService;
import com.lotte.otp.service.UserService;
import com.lotte.otp.util.DateUtils;
import com.lotte.otp.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by choi on 2018. 1. 31. PM 9:31.
 */
@RestController
@RequestMapping(value = "/otp")
public class User2NdAuthController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private User2NdAuthService user2NdAuthService;

    @RequestMapping(value = "/connect/{id}", method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String,Integer>> getOTPConnectStatus(@PathVariable("id") String id) {
        HashMap<String, Integer> result = new HashMap<>();
        ResponseEntity<HashMap<String, Integer>> responseEntity = null;
        if (user2NdAuthService.isUser2NdAuthWithID(id)) {
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
            return responseEntity;
        } else {
            int tempKey = user2NdAuthService.distributeTempkey(id);
            result.put("temp_key", tempKey);
            responseEntity = new ResponseEntity<>(result, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            return responseEntity;
        }
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ModelAndView authenticateOtp(HttpSession httpSession, HttpServletRequest request,
                                        @RequestParam("otp-id") String id, @RequestParam("otp") String otp) {
        String agent = request.getHeader("User-Agent");
        String ipAddress = request.getRemoteAddr();
        String browser = SecurityUtils.getBrowser(agent);
        String os = SecurityUtils.getOS(agent);

        logger.info("ID => " + id + ", OTP => " + otp + ", IP => " + ipAddress + ", Browser => " + browser + ", OS => " + os);
        UserConnectionHistoryVO history = new UserConnectionHistoryVO(ipAddress, os, browser, DateUtils.now());

        if (httpSession.getAttribute("otp-certification") != null) {
            httpSession.setAttribute("otp-certification", true);
            httpSession.setMaxInactiveInterval(60 * 60);
            return new ModelAndView("redirect:/main");
        }

        if (user2NdAuthService.authenticateOtp(id, otp)) {
            logger.info("2차 인증 성공");
            httpSession.setAttribute("otp-certification", true);
            httpSession.setMaxInactiveInterval(60 * 60);
            history.setSuccess(true);
            userService.insertConnectionHistory(id, history);
            return new ModelAndView("redirect:/main");
        }
        history.setSuccess(false);
        userService.insertConnectionHistory(id, history);
        return new ModelAndView("redirect:/login");
    }

}
