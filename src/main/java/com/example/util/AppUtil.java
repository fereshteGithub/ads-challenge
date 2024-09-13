package com.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;


@Component
public class AppUtil {

    public static String objectToJson(Object obj) throws JsonProcessingException {
        if (obj == null) {
            return "";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public String stacktraceToString(Throwable throwable) {
        return ExceptionUtils.getStackTrace(throwable);
    }


}
