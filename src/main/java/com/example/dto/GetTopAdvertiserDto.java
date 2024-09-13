package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTopAdvertiserDto {
    private Integer appId;
    private String countryCode;
    private List<Integer> recommendedAdvertiserIds;
}
