package com.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImpressionDto {
    private UUID id;
    @JsonProperty("app_id")
    private Integer appId;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("advertiser_id")
    private Integer advertiserId;
}
