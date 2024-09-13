package com.example.service;

import com.example.database.repository.ImpressionRepository;
import com.example.dto.GetAdvertiserInfoDto;
import com.example.dto.GetInfoByCountryCodeAndAppId;
import com.example.dto.GetTopAdvertiserDto;
import com.example.dto.ImpressionDto;
import com.example.exception.BusinessException;
import com.example.mapper.ImpressionMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ImpressionService {

    private final ImpressionRepository impressionRepository;
    private final ImpressionMapper impressionMapper;

    public void save(MultipartFile file) {

        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            List<ImpressionDto> impressions = objectMapper.readValue(content,
                    new TypeReference<>() {
                    });
            impressionRepository.saveAll(impressionMapper.toImpression(impressions));
        } catch (IOException e) {
            throw new BusinessException("error.processing.file");
        }
    }


    public List<GetInfoByCountryCodeAndAppId> getInfoByCountryCodeAndAppId(String countryCode,Integer appId) {
        return impressionRepository.getInfoByCountryCodeAndAppId(countryCode,appId);
    }

    public List<GetTopAdvertiserDto> getTopAdvertisers() {
        List<GetAdvertiserInfoDto> allAdvertisers = impressionRepository.findTopAdvertisers();

        return allAdvertisers.stream()
                .collect(Collectors.groupingBy(ad -> new AbstractMap.SimpleEntry<>(ad.getAppId(), ad.getCountryCode())))
                .entrySet().stream()
                .map(entry -> {
                    List<Integer> topAdvertiserIds = entry.getValue().stream()
                            .map(GetAdvertiserInfoDto::getAdvertiserId)
                            .collect(Collectors.toList());

                    Integer appId = entry.getKey().getKey();
                    String countryCode = entry.getKey().getValue();

                    return new GetTopAdvertiserDto(appId, countryCode, topAdvertiserIds);
                })
                .collect(Collectors.toList());
    }


}
