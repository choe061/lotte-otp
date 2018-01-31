package com.lotte.otp.controller;

import com.lotte.otp.domain.UserVO;
import com.lotte.otp.exception.DuplicateUserIDException;
import com.lotte.otp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

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

    @RequestMapping(value = "/duplicate/{id}", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Boolean>> duplicateUserID(@PathVariable("id") String id) {
        logger.info("ID : " + id);
        HashMap<String, Boolean> result = new HashMap<>();
        ResponseEntity<HashMap<String, Boolean>> responseEntity = null;
        try {
            userService.duplicateUserId(id);
            result.put("result", true);
            responseEntity = new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (DuplicateUserIDException e) {
            result.put("result", false);
            responseEntity = new ResponseEntity<>(result, HttpStatus.CONFLICT);
        } finally {
            return responseEntity;
        }
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView signUp(UserVO user) {
        if (userService.createUser(user)) {
            return new ModelAndView("redirect:/login", HttpStatus.CREATED);
        } else {
            return new ModelAndView("/sign-up", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 200 : 정상 응답
     * 204 : 1차 로그인은 성공이지만, OTP 미연동 회원
     * 401 : ID/PW - 1차 로그인 실패
     * 404 : ID가 없음
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Boolean>> login(UserVO user) {
        HashMap<String, Boolean> result = new HashMap<>();

        result.put("result", true);

        ResponseEntity<HashMap<String, Boolean>> responseEntity = null;

        responseEntity = new ResponseEntity<>(result, HttpStatus.OK);

        responseEntity = new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        return null;
    }
}
