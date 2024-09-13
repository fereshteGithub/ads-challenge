package com.example.config.logging;

import com.example.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LogConfiguration {

    private final LoggingService loggingService;
    private final AppUtil appUtil;

    @Bean
    public FilterRegistrationBean<RequestAndResponseLoggingFilter> loggingFilter() {
        FilterRegistrationBean<RequestAndResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestAndResponseLoggingFilter(loggingService, appUtil));
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
