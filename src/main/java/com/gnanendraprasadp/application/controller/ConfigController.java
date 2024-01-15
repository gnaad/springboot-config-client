package com.gnanendraprasadp.application.controller;

import com.gnanendraprasadp.application.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Autowired
    @Qualifier("applicationProperties")
    private ApplicationProperties prop;

    @GetMapping("/get")
    public String getEndPoint() {
        return prop.getUrl() + " " + prop.getUsername();
    }
}
