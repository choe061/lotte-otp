package com.lotte.otp.util;

import com.lotte.otp.exception.KeyTimeoutException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by choi on 2018. 2. 4. PM 5:39.
 */
public class DateUtils {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    private static final ZoneId ZONE_SEOUL = ZoneId.of("Asia/Seoul");

    public static String formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 H시 m분 s초");
        return localDateTime.format(dateTimeFormatter);
    }

    public static String now() {
        return dateFormat.format(new Date());
    }

    public static LocalDateTime currentDateTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZONE_SEOUL).withZoneSameInstant(ZONE_SEOUL).toLocalDateTime();
    }

    public static long convertToLong(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZONE_SEOUL).withZoneSameInstant(ZONE_SEOUL).toInstant()).getTime();
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
    public static String expireMin(Date now, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now.getTime());
        calendar.add(Calendar.MINUTE, min);
        return dateFormat.format(calendar.getTime());
    }

    public static LocalDateTime expireMin(LocalDateTime currentDateTime, int min) {
        return currentDateTime.plusMinutes(min);
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

    public static int remainSeconds(LocalDateTime expiration) {
        LocalDateTime currentTime = DateUtils.currentDateTime(LocalDateTime.now());
        long remainTime = currentTime.until(expiration, ChronoUnit.SECONDS);
        if (remainTime < 0) {
            throw new KeyTimeoutException();
        }
        return (int) remainTime;
    }
}
