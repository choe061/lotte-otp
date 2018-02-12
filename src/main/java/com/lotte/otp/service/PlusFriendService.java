package com.lotte.otp.service;

import com.lotte.otp.domain.KakaoRequestMessageVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PlusFriendService {
    String chat(KakaoRequestMessageVO message);
    String connectWebService(KakaoRequestMessageVO message);
}
