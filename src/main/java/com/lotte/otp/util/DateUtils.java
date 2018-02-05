package com.lotte.otp.util;

import com.lotte.otp.exception.KeyTimeoutException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by choi on 2018. 2. 4. PM 5:39.
 */
public class DateUtils {

    public static String now() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        return dateFormat.format(new Date());
    }

    public static long convertStrToLongDate(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String expireMin(String now, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(convertStrToLongDate(now));
        calendar.add(Calendar.MINUTE, min);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        return dateFormat.format(calendar.getTime());
    }

    public static long remainSeconds(String expiration) {
        long expirationTime = convertStrToLongDate(expiration);
        long currentTime = convertStrToLongDate(now());
        long remainTime = (expirationTime - currentTime) / 1000;
        if (remainTime < 0) {
            throw new KeyTimeoutException();
        }
        return remainTime;
    }
}
