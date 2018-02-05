package com.lotte.otp.util;

import com.lotte.otp.domain.UserVO;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by choi on 2018. 1. 29. PM 5:46.
 */
public class UserValidator {

    private UserValidator() {}

    public static boolean isValidationUserInfo(UserVO user) {
        if (!StringUtils.isAlphanumeric(user.getId())
                || StringUtils.isBlank(user.getId())
                || StringUtils.isEmpty(user.getId())) {
            return false;
        }
        if (user.getId().length() <= 6 || user.getId().length() >= 20) {
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

}
