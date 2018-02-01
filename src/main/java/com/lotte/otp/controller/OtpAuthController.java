package com.lotte.otp.controller;

import com.lotte.otp.domain.SessionScope;
import com.lotte.otp.service.User2NdAuthService;
import com.lotte.otp.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by choi on 2018. 1. 31. PM 9:31.
 */
@RestController
@RequestMapping(value = "/otp")
public class OtpAuthController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private User2NdAuthService user2NdAuthService;
    @Autowired
    private SessionScope sessionScope;

    @RequestMapping(value = "/connect/{id}", method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String,Integer>> getOTPConnectStatus(HttpSession session, @PathVariable("id") String id) {
        HashMap<String, Integer> result = new HashMap<>();
        ResponseEntity<HashMap<String, Integer>> responseEntity = null;
        if (user2NdAuthService.isUser2NdAuth(id)) {
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
            return responseEntity;
        } else {
            int tempKey = SecurityUtils.distributeTempkey();
            sessionScope.setId(id);
            sessionScope.setTemp_key(tempKey);

            session.setAttribute(id, tempKey);
            session.setMaxInactiveInterval(60 * 5); //5분의 유효시간

            result.put("temp_key", tempKey);
            responseEntity = new ResponseEntity<>(result, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            return responseEntity;
        }
    }

}
