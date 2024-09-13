package com.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
//@SecurityRequirement(name = "OAuth2")
@SecurityRequirement(name = "Basic")
//@SecurityRequirement(name = "JWT")
public class TestController {

    @PostMapping("/test")
    public String test() {
        return "ok, ";
    }

}
