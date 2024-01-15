package com.gnanendraprasadp.application.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Aspect
@Component
public class BeforeConfig {

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void refreshConfig() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.postForObject("http://localhost:8080/actuator/refresh", new HttpEntity<>(null, headers), String.class);
    }
}
