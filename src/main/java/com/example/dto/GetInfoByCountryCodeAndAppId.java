package com.example.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetInfoByCountryCodeAndAppId {

    private Integer appId;
    private String countryCode;
    private long clicks;
    private long impressions;
    private double revenue;
}
