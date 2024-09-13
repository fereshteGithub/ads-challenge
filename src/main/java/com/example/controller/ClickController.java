package com.example.controller;

import com.example.service.ClickService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/click")
@RequiredArgsConstructor
@SecurityRequirement(name = "Basic")
public class ClickController {
    private final ClickService clickService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void upload(@RequestParam(value = "file") MultipartFile file) {
        clickService.save(file);
    }
}
