package com.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
//import org.keycloak.representations.AccessTokenResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
@RequiredArgsConstructor
public class HttpUtil {

    private final RestTemplate restTemplate;
    private final AppUtil appUtil;

    public <T> ResponseEntity<T> get(String url) {
        return get(url, null, new ParameterizedTypeReference<T>() {});
    }

    public <T> ResponseEntity<T> get(String url, MultiValueMap<String, String> params) {
        return get(url, params, new ParameterizedTypeReference<T>() {});
    }

    public <T> ResponseEntity<T> get(String url, MultiValueMap<String, String> params, ParameterizedTypeReference<T> responseType) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParams(params);
        return restTemplate.exchange(urlBuilder.toUriString(), HttpMethod.GET,null, responseType);
    }


    public <T> ResponseEntity<T> get(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType) {
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        return restTemplate.exchange(url, HttpMethod.GET,entity, responseType);
    }

    public <T> ResponseEntity<T> get(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType, String userId) {
        HttpEntity<?> request = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, request, responseType,userId);
    }

    public <T, R> ResponseEntity<T> post(String url, R body) {
        return post(url, body, new HttpHeaders(), new ParameterizedTypeReference<T>() {});
    }

//    public ResponseEntity<AccessTokenResponse> post(String url,HttpEntity<MultiValueMap<String, String>> request){
//        return restTemplate.exchange(
//                url,
//                HttpMethod.POST,
//                request,
//                AccessTokenResponse.class
//        );
//    }

    public <T, R> ResponseEntity<T> post(String url, R body, MultiValueMap<String, String> headers) {
        return post(url, body, headers, new ParameterizedTypeReference<T>() {});
    }


    public <T, R> ResponseEntity<T> post(String url, R body, MultiValueMap<String, String> headers, ParameterizedTypeReference<T> responseType) {
        HttpEntity<R> request = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.POST, request, responseType);
    }

    public <T, R> ResponseEntity<T> put(String url, R body) {
        return put(url, body, null, new ParameterizedTypeReference<T>() {});
    }

//    public ResponseEntity<String> put(String url, UpdateUserRepresentationDto body, HttpHeaders headers, String userId) {
//        HttpEntity<UpdateUserRepresentationDto> request = new HttpEntity<>(body, headers);
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class, userId);
//        return response;
//    }

    public <T, R> ResponseEntity<T> put(String url, R body, HttpHeaders headers, ParameterizedTypeReference<T> responseType, String userId) {
        HttpEntity<R> request = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, request, responseType,userId);
    }

    public <T, R> ResponseEntity<T> put(String url, R body, MultiValueMap<String, String> headers, ParameterizedTypeReference<T> responseType) {
        HttpEntity<R> request = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, request, responseType);
    }

    public <T> ResponseEntity<T> delete(String url, MultiValueMap<String, String> headers) {
        return delete(url, headers, new ParameterizedTypeReference<T>() {});
    }

    public <T> ResponseEntity<T> delete(String url, MultiValueMap<String, String> headers, ParameterizedTypeReference<T> responseType) {
        HttpEntity<?> request = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.DELETE, request, responseType);
    }

    public <T> ResponseEntity<T> delete(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType, String userId) {
        HttpEntity<?> request = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.DELETE, request, responseType,userId);
    }


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
            Map<String, String> headers = getHeaders(request);
            headers.replace("authorization", "**********");
            headers.replace("Authorization", "**********");
            return appUtil.objectToJson(headers);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

//    public String getHeadersToJson(HttpHeaders responseHeaders) {
//        try {
//            Map<String, String> _headers = new HashMap<>(responseHeaders.toSingleValueMap());
//            _headers.replace("authorization", "**********");
//            _headers.replace("Authorization", "**********");
//            return appUtil.objectToJson(_headers);
//        } catch (JsonProcessingException e) {
//            return "";
//        }
//    }

    private String getRequestUsername(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }


}


