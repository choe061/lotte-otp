package com.lotte.otp.domain.type;

/**
 * Created by choi on 2018. 8. 18. AM 1:13.
 */
public enum Button {
    REGIST_ID_BUTTON("ID 등록"),
    REQUEST_OTP_BUTTON("OTP (재)발급"),
    OTP_EXPIRATION_TIME_BUTTON("OTP 만료 시간 확인"),
    LOGIN_HISTORY_BUTTON("로그인 내역 확인"),

    UNKNOWN("Unknown Button Type.");

    private String title;

    Button(String title) {
        this.title = title;
    }

    public static Button findByButtonTitle(String title) {
        for (Button button : Button.values()) {
            if (button.title.equals(title)) {
                return button;
            }
        }
        return UNKNOWN;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                '}';
    }
}
