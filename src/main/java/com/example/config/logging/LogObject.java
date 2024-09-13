package com.example.config.logging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import java.util.Date;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogObject {
    public enum LogCase {START, END, EXCEPTION}

    private String requestId;
    private Date dateTime;
    private String action;
    private Map<String, String> headers;
    private String payload;
    private LogCase logCase;
    private LogLevel level;
    private String stacktrace;
    private String requestMethod;
    private Long elapsed;
}
