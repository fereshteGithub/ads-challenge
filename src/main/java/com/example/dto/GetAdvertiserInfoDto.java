package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAdvertiserInfoDto {
    private Integer appId;
    private String countryCode;
    private double revenue;
    private Integer advertiserId;
}
