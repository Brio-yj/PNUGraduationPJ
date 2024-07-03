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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public ResponseEntity applyPod(Map<String, Object> metadata) {
        // 기본 Pod 사양 정의
        Map<String, Object> defaultPodSpec = new HashMap<>();
        defaultPodSpec.put("apiVersion", "v1");
        defaultPodSpec.put("kind", "Pod");

        // metadata 설정
        defaultPodSpec.put("metadata", metadata);

        // 기본 container 설정
        Map<String, Object> container = new HashMap<>();
        container.put("name", "default-container");
        container.put("image", "nginx:latest");

        Map<String, Object> spec = new HashMap<>();
        List<Map<String, Object>> containers = new ArrayList<>();
        containers.add(container);
        spec.put("containers", containers);

        defaultPodSpec.put("spec", spec);

        String url = API_SERVER + API_URL + RESOURCE_TYPE_POD;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(defaultPodSpec, headers);

        try {
            String podName = (String) metadata.get("name");
            String getUrl = API_SERVER + API_URL + RESOURCE_TYPE_POD + "/" + podName;
            restTemplate.exchange(getUrl, HttpMethod.GET, requestEntity, Map.class);
            return ResponseEntity.ok("updated pod");
        } catch (HttpClientErrorException.NotFound e) {
            restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
            return ResponseEntity.ok("created pod");
        }
    }
}
