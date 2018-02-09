package com.lotte.otp.controller;

import com.lotte.otp.domain.UserConnectionHistoryVO;
import com.lotte.otp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by choi on 2018. 2. 3. AM 12:29.
 */
@RestController
@RequestMapping()
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/main")
    public ModelAndView home() {
        return new ModelAndView("main");
    }

    @RequestMapping(value = "/menu/history", method = RequestMethod.GET)
    public ModelAndView myConnectionHistory(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView("history");
        ArrayList<UserConnectionHistoryVO> connectionHistory
                = userService.getAllConnectionHistoryWithId(String.valueOf(httpSession.getAttribute("id")));
        modelAndView.addObject("myHistory", connectionHistory);
        return modelAndView;
    }
}
