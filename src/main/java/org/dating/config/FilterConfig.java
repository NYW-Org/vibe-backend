package org.dating.config;

import org.dating.handler.JwtFilterHandler;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtFilterHandler> jwtFilterHandlerFilterRegistrationBean(JwtFilterHandler jwtFilterHandler) {
        FilterRegistrationBean<JwtFilterHandler> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilterHandler);
        registrationBean.addUrlPatterns("/api/chat");
        return registrationBean;
    }
}
