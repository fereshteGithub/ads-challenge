package com.example.config.logging;

import com.example.exception.ApiError;
import com.example.exception.BusinessException;
import com.example.util.AppUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RequestAndResponseLoggingFilter extends OncePerRequestFilter {

    private final LoggingService loggingService;
    private final AppUtil appUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String requestId = UUID.randomUUID().toString();
        request.setAttribute("requestId", requestId);
        Date requestDatetime = new Date();

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } catch (Exception ex) {
            stopWatch.stop();
            String requestBody = this.getRequestPayload(wrappedRequest);
            loggingService.logRequest(request, requestDatetime, requestBody);

            loggingService.logException(wrappedRequest, wrappedResponse, ex, stopWatch.getTotalTimeMillis());

            ex.printStackTrace();

            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Something went wrong", ex.getCause());
            if (ex.getCause() instanceof BusinessException) {
                wrappedResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            } else {
                wrappedResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            wrappedResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            wrappedResponse.getWriter().write(appUtil.objectToJson(apiError));
            wrappedResponse.copyBodyToResponse();
            return;
        }

        stopWatch.stop();

        // I can only log the request's body AFTER the request has been made and ContentCachingRequestWrapper did its work.
        String requestBody = this.getRequestPayload(wrappedRequest);
        loggingService.logRequest(request, requestDatetime, requestBody);

        String responseBody = getResponsePayload(wrappedResponse);
        loggingService.logResponse(request, response, responseBody, stopWatch.getTotalTimeMillis());

        wrappedResponse.copyBodyToResponse();
    }

    private String getRequestPayload(ContentCachingRequestWrapper request) {
        return new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
    }

    private String getResponsePayload(ContentCachingResponseWrapper response) {
        return response != null ? new String(response.getContentAsByteArray()) : null;
    }

}