package com.lotte.otp.util;

import com.lotte.otp.domain.User;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by choi on 2018. 1. 29. PM 5:46.
 */
public class UserValidator {

    private UserValidator() {}

    public static boolean isValidationUserInfo(User user) {
        if (!isValidationId(user.getId())) {
            return false;
        }
        if (StringUtils.isBlank(user.getPw()) || StringUtils.isEmpty(user.getPw())) {
            return false;
        }
        if (!user.getPw().equals(user.getPw2())) {
            return false;
        }
        if (user.getPw().length() < 6 || user.getPw().length() > 30) {
            return false;
        }
        return true;
    }

    public static boolean isValidationLoginInfo(User user) {
        if (StringUtils.isBlank(user.getId()) || StringUtils.isEmpty(user.getId())) {
            return false;
        }
        if (StringUtils.isBlank(user.getPw()) || StringUtils.isEmpty(user.getPw())) {
            return false;
        }
        return true;
    }

    public static boolean isValidationId(String id) {
        if (!StringUtils.isAlphanumeric(id)
                || StringUtils.isEmpty(id) || StringUtils.isBlank(id)
                || id.length() < 5 || id.length() > 15) {
            return false;
        }
        return true;
    }

}
