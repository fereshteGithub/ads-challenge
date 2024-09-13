package com.example.controller;

import com.example.service.ClickService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/click")
@RequiredArgsConstructor
@SecurityRequirement(name = "Basic")
@Tag(name = "Clicks")
public class ClickController {
    private final ClickService clickService;

    @Operation(
            description = "Upload impression file,process its content and store data in Database",
            tags = {"Clicks"}
    )

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void upload(@RequestParam(value = "file") MultipartFile file) {
        clickService.save(file);
    }
}
