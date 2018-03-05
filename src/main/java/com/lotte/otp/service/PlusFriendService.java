package com.lotte.otp.service;

import com.lotte.otp.domain.KakaoRequestMessage;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PlusFriendService {
    String chat(KakaoRequestMessage message);
    String connectWebService(KakaoRequestMessage message);
}
