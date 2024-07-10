package com.example.demo.service;

import com.example.demo.dto.PodDto;
import com.example.demo.util.GetUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetService {
    private final GetUtil getUtil;

    public List getResource(String resourceType) {
        Map response = (Map) getUtil.execute(HttpMethod.GET, resourceType).getBody();

        List<Map> items = (List) response.get("items");

        return items.stream()
                .map(item -> {
                    Map metadata = (Map) item.get("metadata");
                    String name = (String) metadata.get("name");
                    String namespace = (String) metadata.get("namespace");
                    Map status = (Map) item.get("status");
                    String phase = (String) status.get("phase");

                    return PodDto.builder()
                            .name(name)
                            .status(phase)
                            .namespace(namespace)
                            .build();
                })
                .collect(Collectors.toList());
    }
}