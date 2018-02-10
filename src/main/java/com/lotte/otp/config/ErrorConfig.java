package com.lotte.otp.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Created by choi on 2018. 2. 10. PM 8:29.
 */
@Configuration
public class ErrorConfig extends ServerProperties {
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        super.customize(container);
        container.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html"),
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"),
                new ErrorPage("/error/404")
        );
    }
}
