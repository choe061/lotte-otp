package com.lotte.otp.controller;

import com.lotte.otp.service.User2NdAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class User2NdAuthController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

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
            logger.info("temp key = " + String.valueOf(tempKey));
            result.put("temp_key", tempKey);
            responseEntity = new ResponseEntity<>(result, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            return responseEntity;
        }
    }

}
