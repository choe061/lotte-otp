package com.lotte.otp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by choi on 2018. 1. 30. PM 2:50.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        // 메인페이지 : css나 js 같은것들도 여기에 포함시켜준다.
        web.ignoring().antMatchers("/user", "/sign-up", "/login", "/otp");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()    // 위 ignoring 을 제외한 전체가 기본 인증페이지
                .antMatchers("/main/**")
                .authenticated()
                .and()
                .csrf().disable();

        http.formLogin()    // 로그인 페이지 : 컨트롤러 매핑을 하지 않으면 기본 제공되는 로그인 페이지가 뜬다.
                .loginPage("/login")
//                .loginProcessingUrl("/user/login")
                .failureUrl("/login");
//                .successHandler(successHandler());

        http.logout()   // /logout 을 호출할 경우 로그아웃
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login"); // 로그아웃이 성공했을 경우 이동할 페이지
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new OtpAuthHandler();
    }

    class OtpAuthHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {

        }
    }
}
