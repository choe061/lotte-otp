package com.lotte.otp.util;

import com.lotte.otp.domain.UserConnectionHistoryVO;
import com.lotte.otp.exception.KeyTimeoutException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by choi on 2018. 2. 4. PM 5:39.
 */
public class DateUtils {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);

    public static String splitTime(String date) {
        return date.substring(0, date.length() - 2);
    }

    public static String now() {
        return dateFormat.format(new Date());
    }

    public static long convertStringDateToLongDate(String strDate) {
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 만료 시간을 계산해주는 기능
     * @param now
     * @param min
     * @return
     */
    public static String expireMin(String now, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(DateUtils.convertStringDateToLongDate(now));
        calendar.add(Calendar.MINUTE, min);
        return dateFormat.format(calendar.getTime());
    }

    /**
    * 남은 초를 반환
     * @param expiration
     * @return
     */
    public static int remainSeconds(String expiration) {
        long expirationTime = DateUtils.convertStringDateToLongDate(expiration);
        long currentTime = DateUtils.convertStringDateToLongDate(DateUtils.now());
        long remainTime = (expirationTime - currentTime) / 1000;
        if (remainTime < 0) {
            throw new KeyTimeoutException();
        }
        return (int) remainTime;
    }
}
