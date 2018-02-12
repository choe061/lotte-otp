package com.lotte.otp.controller;

import com.lotte.otp.domain.UserAuthStatus;
import com.lotte.otp.domain.UserConnectionHistoryVO;
import com.lotte.otp.domain.UserVO;
import com.lotte.otp.exception.DuplicateUserIDException;
import com.lotte.otp.service.User2NdAuthService;
import com.lotte.otp.service.UserService;
import com.lotte.otp.util.SecurityUtils;
import com.lotte.otp.util.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    @Autowired
    private User2NdAuthService user2NdAuthService;

    @RequestMapping(value = "/home")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/duplicate/{id}", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Boolean>> duplicateUserID(@PathVariable("id") String id) {
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
        if (UserValidator.isValidationUserInfo(user)) {
            return new ModelAndView("redirect:/sign-up", HttpStatus.NO_CONTENT);
        }
        if (userService.createUser(user)) {
            return new ModelAndView("redirect:/login", HttpStatus.CREATED);
        } else {
            return new ModelAndView("redirect:/sign-up", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 200 : 정상 응답                          OK - OTP입력 팝업
     * 203 : 1차 로그인은 성공이지만, OTP 미연동 회원 NO_CONTENT - OTP연동 팝업(qrcode 게시 등)
     * 401 : ID/PW - 1차 로그인 실패             UNAUTHORIZED - 다시 입력
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Boolean>> login(HttpSession httpSession, @RequestBody UserVO user) {
        HashMap<String, Boolean> result = new HashMap<>();
        ResponseEntity<HashMap<String, Boolean>> responseEntity = null;

        if (httpSession.getAttribute("first-certification") != null) {  //세션이 있는 경우 그냥 패스
            result.put("result", true);
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
            return responseEntity;
        }

        UserAuthStatus authStatus = userService.login(user.getId(), user.getPw());

        if (authStatus == UserAuthStatus.OK) {
            httpSession.setAttribute("id", user.getId());
            httpSession.setAttribute("first-certification", true);
            httpSession.setMaxInactiveInterval(5 * 60);

            result.put("result", true);
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("result", false);
            responseEntity = new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ModelAndView myConnectionHistory(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView("history");
        ArrayList<UserConnectionHistoryVO> connectionHistory
                = userService.getAllConnectionHistoryWithId(String.valueOf(httpSession.getAttribute("id")));
        modelAndView.addObject("myHistory", connectionHistory);
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession httpSession) {
        SecurityUtils.clearSession(httpSession);
        return new ModelAndView("redirect:/login");
    }

}
