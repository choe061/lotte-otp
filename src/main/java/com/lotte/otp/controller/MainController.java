package com.lotte.otp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by choi on 2018. 2. 3. AM 12:29.
 */
@RestController
@RequestMapping(value = "/main")
public class MainController {

    @RequestMapping(value = "/history")
    public ModelAndView history() {
        return new ModelAndView("history");
    }

    @RequestMapping(value = "/main")
    public ModelAndView main() {
        return new ModelAndView("sign-up");
    }
}
