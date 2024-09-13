package com.example.controller;

import com.example.service.FileService;
import com.example.service.ImpressionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
@SecurityRequirement(name = "Basic")
@Tag(name = "Report")
public class ReportController {

    private final ImpressionService impressionService;
    private final FileService fileService;

    @Operation(
            description = " provide information depending on the countryCode and appId",
            tags = {"Report"}
    )


    @GetMapping("/get-info-by-country-code-and-app-id/{country-code}/{app-id}")
    public ResponseEntity<byte[]> getInfoByCountryCodeAndAppId(@PathVariable("country-code") String countryCode
            , @PathVariable("app-id") Integer appId) throws Exception {

        byte[] jsonBytes = fileService.convertListToJsonFile(impressionService.getInfoByCountryCodeAndAppId(countryCode,appId));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", "result.json");
        headers.setContentLength(jsonBytes.length);
        return new ResponseEntity<>(jsonBytes, headers, HttpStatus.OK);
    }

    @Operation(
            description = "make a recommendation for the top 5 advertiser",
            tags = {"Report"}
    )
    @GetMapping("/recommendation-top-advertiser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getTop5AdvertiserId() throws Exception {
        byte[] jsonBytes = fileService.convertListToJsonFile(impressionService.getTopAdvertisers());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentDispositionFormData("attachment", "topAdviser.json");
        headers.setContentLength(jsonBytes.length);
        return new ResponseEntity<>(jsonBytes, headers, HttpStatus.OK);
    }
}
