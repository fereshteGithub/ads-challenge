package com.example.config;

import com.example.config.interceptor.RestTemplateLoggingInterceptor;
import com.example.config.logging.LoggingService;
import com.example.exception.RestTemplateResponseErrorHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jetbrains.annotations.NotNull;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.*;

@Component
@RequiredArgsConstructor
public class SystemConfig {

    private final LoggingService loggingService;
    private final HttpServletRequest httpServletRequest;

//    @Value("${auth.server.public.key}")
//    private String publicKey;
//
//    @Value("${auth.realm}")
//    private String authRealm;
//
//    @Value("${auth.server.url}")
//    private String authServerUrl;
//
//    @Value("${auth.client.secret}")
//    private String clientSecret;
//
//    @Value("${auth.client.id}")
//    private String clientId;

    @Value("${allowedCors}")
    private String allowedCors;


    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Bean
    public RestTemplate restTemplate(@NotNull RestTemplateBuilder restTemplateBuilder) {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setOutputStreaming(false);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory);
        return restTemplateBuilder
                .requestFactory(() -> factory)
                .setConnectTimeout(Duration.ofMinutes(2))
                .setReadTimeout(Duration.ofMinutes(2))
                .defaultHeader("Accept", MediaType.ALL_VALUE)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .errorHandler(new RestTemplateResponseErrorHandler())
                .interceptors(new RestTemplateLoggingInterceptor(loggingService, httpServletRequest))
                .build();
    }

    @Bean
    public Boolean disableSSLValidation() throws Exception {
        final SSLContext sslContext = SSLContext.getInstance("TLS");

        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, null);

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        return true;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        //config.setAllowedOrigins(List.of(allowedCors.split(",")));
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        //config.setAllowedHeaders(List.of("Authorization"));
        config.setAllowedHeaders(List.of("*"));
        ///todo: setAllowCredentials must set true
        config.setAllowCredentials(false);
        config.setMaxAge(Duration.ofSeconds(1800));

        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/api-docs", config);
        source.registerCorsConfiguration("/swagger-ui/**", config);

        return new CorsFilter(source);
    }

//    @Bean
//    public JwtParser jwtParser() throws Exception {
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
//        PublicKey publicKey = kf.generatePublic(keySpec);
//        return Jwts.parserBuilder().setSigningKey(publicKey).build();
//    }

//    @Bean
//    public Keycloak keycloak() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
//        TrustManager[] trustAllCerts = new TrustManager[]{
//                new X509TrustManager() {
//                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[0];
//                    }
//
//                    public void checkClientTrusted(
//                            java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//
//                    public void checkServerTrusted(
//                            java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//                }
//        };
//        SSLContext sslContext = SSLContext.getInstance("SSL");
//        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//
//        return KeycloakBuilder.builder()
//                .serverUrl(authServerUrl)
//                .realm(authRealm)
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .grantType("client_credentials")
//                .resteasyClient(ResteasyClientBuilder.newBuilder()
//                        .sslContext(sslContext)
//                        .hostnameVerifier(NoopHostnameVerifier.INSTANCE).build())
//                .build();
//    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource =
                new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(new Locale("fa"));
        return messageSource;
    }

}
