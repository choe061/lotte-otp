package com.lotte.otp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by choi on 2018. 1. 29. PM 2:31.
 */
@RestController
@RequestMapping("/kakaoApi")
public class ChatController {

    @RequestMapping(value = "/keyboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getKeyboard() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
