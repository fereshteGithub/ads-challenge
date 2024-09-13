package com.example.config.interceptor;

import com.example.config.logging.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final LoggingService loggingService;
    private final HttpServletRequest httpServletRequest;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String requestId = (String) httpServletRequest.getAttribute("requestId");
        loggingService.logRequest(request, requestId, new String(body, StandardCharsets.UTF_8));

        ClientHttpResponse response = execution.execute(request, body);

        stopWatch.stop();
        String responseBody = "Response To Body";
        try {
            responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        }
        catch (Exception ignored){}

        loggingService.logResponse(request, response, requestId, responseBody, stopWatch.getTotalTimeMillis());


        return response;
    }
}
