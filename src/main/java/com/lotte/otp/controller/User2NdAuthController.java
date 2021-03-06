package com.lotte.otp.controller;

import com.lotte.otp.domain.BlockUser;
import com.lotte.otp.domain.type.UserAuthStatus;
import com.lotte.otp.domain.UserConnectionHistory;
import com.lotte.otp.service.ChatRedisService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    private ChatRedisService chatRedisService;

    @RequestMapping(value = "/connect/{id}", method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String,Integer>> getOTPConnectStatus(@PathVariable("id") String id) {
        if (!UserValidator.isValidationId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        HashMap<String, Integer> result = new HashMap<>();
        ResponseEntity<HashMap<String, Integer>> responseEntity = null;

        if (UserAuthStatus.CONNECTION_OTP == user2NdAuthService.isUser2NdAuthWithID(id)) {
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
            return responseEntity;
        } else {
            int tempKey = chatRedisService.distributeTempKey(id);
            result.put("temp_key", tempKey);
            responseEntity = new ResponseEntity<>(result, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            return responseEntity;
        }
    }

    @RequestMapping(value = "/auth/{otp}", method = RequestMethod.GET
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, Object>> authenticateOtp(HttpSession httpSession, HttpServletRequest request
            , @PathVariable("otp") String otp) {
        Map<String, Object> responseBody = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        String agent = request.getHeader("User-Agent");
        String ipAddress = request.getRemoteAddr();
        String browser = SecurityUtils.getClientBrowser(agent);
        String os = SecurityUtils.getClientOS(agent);
        String id = String.valueOf(httpSession.getAttribute("id"));

        logger.info("[OTP] ID => " + id + ", OTP => " + otp + ", IP => " + ipAddress + ", Browser => " + browser + ", OS => " + os);
        UserConnectionHistory history = new UserConnectionHistory(ipAddress, os, browser);

        if (user2NdAuthService.getBlockUserIp(id, ipAddress)) {
            responseBody.put("reason", "해당 아이디에 접근할 수 없습니다. 접속하려면 문의주세요.");
            responseEntity = new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE); //406
            userService.insertConnectionHistory(false, id, history);
            return responseEntity;
        }

        if (httpSession.getAttribute("attempt") != null) {
            if (SecurityUtils.isBlockUserIp(httpSession, new BlockUser(id, ipAddress))) {
                user2NdAuthService.blockUserIp(new BlockUser(id, ipAddress));
                responseBody.put("reason", "5회연속 틀려 접근이 제한되었습니다. 접속하려면 문의주세요.");
                responseEntity = new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE); //406
                httpSession.removeAttribute("attempt");
                userService.insertConnectionHistory(false, id, history);
                return responseEntity;
            }
        } else {
            httpSession.setAttribute("attempt", new BlockUser(id, ipAddress, 0));
        }

        if (httpSession.getAttribute("otp-certification") != null) {
            httpSession.setAttribute("otp-certification", true);
            httpSession.setMaxInactiveInterval(60 * 60);
            responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK); //200
            return responseEntity;
        }

        if (user2NdAuthService.authenticateOtp(id, otp)) {
            logger.info("2차 인증 성공");
            httpSession.setAttribute("otp-certification", true);
            httpSession.setMaxInactiveInterval(60 * 60);
            userService.insertConnectionHistory(true, id, history);
            httpSession.removeAttribute("attempt");
            responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK); //200
            return responseEntity;
        }

        userService.insertConnectionHistory(false, id, history);
        int count = ((BlockUser) httpSession.getAttribute("attempt")).getCount() + 1;
        responseBody.put("reason", "ID/OTP가 " + count + "번째 틀렸습니다. 5회연속 틀린경우 접속에 제한이 있습니다.");
        responseEntity = new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);   //401
        return responseEntity;
    }

}
