package com.example.demo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PostUtil {

    @Value("${config.kubernetes.url}")
    private String API_SERVER;

    @Value("${config.kubernetes.token}")
    private String API_TOKEN;

    private final RestTemplate restTemplate;
    private final String API_URL = "/api/v1/namespaces/default/";
    public static final String RESOURCE_TYPE_POD = "pods";

    public ResponseEntity applyPod(Map<String, Object> podSpec) {
        String url = API_SERVER + API_URL + RESOURCE_TYPE_POD;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(podSpec, headers);

        try {

            String podName = (String)((Map<String, Object>) podSpec.get("metadata")).get("name");
            String getUrl = API_SERVER + API_URL + RESOURCE_TYPE_POD + "/" + podName;
            restTemplate.exchange(getUrl, HttpMethod.GET, requestEntity, Map.class);

            restTemplate.exchange(getUrl, HttpMethod.PUT, requestEntity, Map.class);

            return ResponseEntity.ok("updated pod");
        } catch (HttpClientErrorException.NotFound e) {
            restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

            return ResponseEntity.ok("created pod");
        }
    }
}
