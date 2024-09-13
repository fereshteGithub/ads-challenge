package com.example.service;


import com.example.database.entity.Click;
import com.example.database.repository.ClickRepository;
import com.example.database.repository.ImpressionRepository;
import com.example.dto.ClickDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClickService {

    private final ClickRepository clickRepository;
    private final ImpressionRepository impressionRepository;

    public void save(MultipartFile file) {

        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            List<ClickDto> clickDtoList = objectMapper.readValue(content,
                    new TypeReference<>() {
                    });

          List<Click>  clicks = clickDtoList.stream().map(clickDto -> Click.builder().
                  revenue(clickDto.getRevenue())
                  .impression(impressionRepository.findById(clickDto.getImpressionId()).orElseThrow()).build()).collect(Collectors.toList());

            clickRepository.saveAll(clicks);


        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing file", e);
        }

    }


}
