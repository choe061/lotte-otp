package com.lotte.otp.controller;

import com.lotte.otp.service.User2NdAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/connect/{id}", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Boolean>> getOTPConnectStatus(@PathVariable("id") String id) {
        HashMap<String, Boolean> result = new HashMap<>();
        ResponseEntity<HashMap<String, Boolean>> responseEntity = null;
        if (user2NdAuthService.isUser2NdAuth(id)) {
            result.put("result", true);
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("result", false);
            responseEntity = new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

}
