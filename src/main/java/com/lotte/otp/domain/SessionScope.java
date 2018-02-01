package com.lotte.otp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by choi on 2018. 2. 1. PM 3:51.
 */
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionScope implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private int temp_key;
}
