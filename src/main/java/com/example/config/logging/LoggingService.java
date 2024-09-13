package com.example.config.logging;

import com.example.util.AppUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoggingService {

    private final AppUtil appUtil;
    private final HttpRequestUtil httpRequestUtil;

    public void logRequest(HttpServletRequest request, String body) {
        String requestId = (String) request.getAttribute("requestId");
        LogObject logObject = LogObject.builder()
                                       .requestId(requestId)
                                       .logCase(LogObject.LogCase.START)
                                       .dateTime(new Date())
                                       .action(httpRequestUtil.getFullURL(request))
                                       .level(LogLevel.INFO)
                                       .headers(httpRequestUtil.getHeaders(request))
                                       .payload(body)
                                       .requestMethod(request.getMethod())
                                       .build();

        try {
            log.info(appUtil.objectToJson(logObject));
        } catch (JsonProcessingException e) {
            logException(request, null, e, null);
        }
    }

    public void logRequest(HttpServletRequest request, Date datetime, String body) {
        String requestId = (String) request.getAttribute("requestId");
        LogObject logObject = LogObject.builder()
                                       .requestId(requestId)
                                       .logCase(LogObject.LogCase.START)
                                       .dateTime(datetime)
                                       .action(httpRequestUtil.getFullURL(request))
                                       .level(LogLevel.INFO)
                                       .headers(httpRequestUtil.getHeaders(request))
                                       .payload(body)
                                       .requestMethod(request.getMethod())
                                       .build();

        try {
            log.info(appUtil.objectToJson(logObject));
        } catch (JsonProcessingException e) {
            logException(request, null, e, null);
        }
    }

    public void logRequest(HttpRequest request, String requestId, String body) {
        LogObject logObject = LogObject.builder()
                                       .requestId(requestId)
                                       .logCase(LogObject.LogCase.START)
                                       .dateTime(new Date())
                                       .action(request.getURI().toString())
                                       .level(LogLevel.INFO)
                                       .headers(httpRequestUtil.getHeaders(request))
                                       .payload(body)
                                       .requestMethod(request.getMethod().toString())
                                       .build();

        try {
            log.info(appUtil.objectToJson(logObject));
        } catch (JsonProcessingException e) {
            logException(request, null, requestId, e);
        }
    }

    public void logResponse(HttpServletRequest request, HttpServletResponse response, String body, Long elapsed) {
        String requestId = (String) request.getAttribute("requestId");
        LogObject logObject = LogObject.builder()
                                       .requestId(requestId)
                                       .logCase(LogObject.LogCase.END)
                                       .dateTime(new Date())
                                       .action(httpRequestUtil.getFullURL(request))
                                       .level(LogLevel.INFO)
                                       .headers(httpRequestUtil.getHeaders(response))
                                       .payload(body)
                                       .elapsed(elapsed)
                                       .build();

        try {
            log.info(appUtil.objectToJson(logObject));
        } catch (JsonProcessingException e) {
            logException(request, response, e, elapsed);
        }
    }

    public void logResponse(HttpRequest request, ClientHttpResponse response, String requestId, String body, Long elapsed) {
        LogObject logObject = LogObject.builder()
                                       .requestId(requestId)
                                       .logCase(LogObject.LogCase.END)
                                       .dateTime(new Date())
                                       .action(request.getURI().toString())
                                       .level(LogLevel.INFO)
                                       .headers(httpRequestUtil.getHeaders(response))
                                       .payload(body)
                                       .elapsed(elapsed)
                                       .build();

        try {
            log.info(appUtil.objectToJson(logObject));
        } catch (JsonProcessingException e) {
            logException(request, response, requestId, e);
        }
    }

    public void logException(HttpServletRequest request, HttpServletResponse response, Throwable ex, Long elapsed) {
        String requestId = (String) request.getAttribute("requestId");
        LogObject logObject = LogObject.builder()
                                       .requestId(requestId)
                                       .logCase(LogObject.LogCase.EXCEPTION)
                                       .dateTime(new Date())
                                       .action(httpRequestUtil.getFullURL(request))
                                       .level(LogLevel.ERROR)
                                       .headers(response != null ? httpRequestUtil.getHeaders(response) : null)
                                       .payload(ex.getMessage())
                                       .elapsed(elapsed)
                                       .build();

        logObject.setStacktrace(appUtil.stacktraceToString(ex));
        try {
            log.error(appUtil.objectToJson(logObject));
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
    }

    public void logException(HttpRequest request, ClientHttpResponse response, String requestId, Throwable ex) {
        LogObject logObject = LogObject.builder()
                                       .requestId(requestId)
                                       .logCase(LogObject.LogCase.EXCEPTION)
                                       .dateTime(new Date())
                                       .action(request.getURI().toString())
                                       .level(LogLevel.ERROR)
                                       .headers(response != null ? httpRequestUtil.getHeaders(response) : null)
                                       .payload(ex.getMessage())
                                       .build();

        logObject.setStacktrace(appUtil.stacktraceToString(ex));
        try {
            log.error(appUtil.objectToJson(logObject));
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
    }
}