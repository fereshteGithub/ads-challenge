package com.example.controller;

import com.example.service.FileService;
import com.example.service.ImpressionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
@SecurityRequirement(name = "Basic")
public class ReportController {

    private final ImpressionService impressionService;
    private final FileService fileService;


    @GetMapping("/get-info-by-country-code-and-app-id")
    public ResponseEntity<byte[]> getInfoByCountryCodeAndAppId() throws Exception{

        byte[] jsonBytes = fileService.convertListToJsonFile(impressionService.getInfoByCountryCodeAndAppId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", "result.json");
        headers.setContentLength(jsonBytes.length);
        return new ResponseEntity<>(jsonBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/recommendation-top-advertiser")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<byte[]> getTop5AdvertiserId() throws Exception{
        byte[] jsonBytes = fileService.convertListToJsonFile(impressionService.getTopAdvertisers());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", "topAdviser.json");
        headers.setContentLength(jsonBytes.length);
        return new ResponseEntity<>(jsonBytes, headers, HttpStatus.OK);
    }
}
