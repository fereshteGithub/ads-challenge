package com.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class HttpRequestUtil {

    private final AppUtil appUtil;

    public String getIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-FORWARDED-FOR");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor;
        }
        return request.getRemoteAddr();
    }

    public String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    public Map<String, String> getHeaders(HttpRequest request) {
        return request.getHeaders().toSingleValueMap();
    }

    public Map<String, String> getHeaders(ClientHttpResponse response) {
        return response.getHeaders().toSingleValueMap();
    }

    public Map<String, String> getHeaders(HttpServletResponse response) {
        return response.getHeaderNames()
                       .stream()
                       .distinct()
                       .collect(Collectors.toMap(key -> key, response::getHeader));
    }

    public Map<String, String> getHeaders(HttpServletRequest request) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(request.getHeaderNames().asIterator(), Spliterator.ORDERED),
                                    false)
                            .distinct()
                            .collect(Collectors.toMap(key -> key, request::getHeader));
    }

    public String getHeadersToJson(HttpServletRequest request) {
        try {
            return appUtil.objectToJson(getHeaders(request));
        } catch (JsonProcessingException e) {
            return "";
        }

    }

}
