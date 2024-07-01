package com.example.demo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GetUtil {
    @Value("${config.kubernetes.url}")
    private String API_SERVER;
    @Value("${config.kubernetes.token}")
    private String API_TOKEN;
    private final RestTemplate restTemplate;
    private final String API_URL = "/api/v1/namespaces/default/";
    public static final String RESOURCE_TYPE_POD = "pods";

    public ResponseEntity execute(HttpMethod httpMethod, String resourceType) {
        String url = API_SERVER + API_URL + resourceType;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        return restTemplate.exchange(url, httpMethod, httpEntity, Map.class);
    }
}
