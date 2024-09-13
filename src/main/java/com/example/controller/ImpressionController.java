package com.example.controller;


import com.example.service.ImpressionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/impression")
@RequiredArgsConstructor
@SecurityRequirement(name = "Basic")
@Tag(name = "Impression")
public class ImpressionController {


    private final ImpressionService impressionService;

    @Operation(
            description = "Upload impression file,process its content and store data in Database",
            tags = {"Impression"}
    )

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void upload(@RequestParam(value = "file") MultipartFile file) {
        impressionService.save(file);
    }
}
