package com.lotte.otp.config;

import com.lotte.otp.util.CertificationInterceptor;
import com.lotte.otp.util.OTPCertificationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by choi on 2018. 2. 5. PM 10:17.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CertificationInterceptor interceptor;
    @Autowired
    private OTPCertificationInterceptor otpInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**", "/kakaoApi/**", "/sign-up", "/login");

        registry.addInterceptor(otpInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**", "/kakaoApi/**", "/sign-up", "/login");
    }
}
