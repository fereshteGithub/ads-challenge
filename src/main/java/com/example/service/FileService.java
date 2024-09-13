package com.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class FileService {
    private final ObjectMapper objectMapper;
    public  byte[] convertListToJsonFile(Object object) throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        objectMapper.writeValue(out, object);
        return out.toByteArray();
    }
}
