package com.example.demo.service;

import com.example.demo.dto.PodDto;
import com.example.demo.util.GetUtil;
import com.example.demo.util.PostUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostUtil postUtil;
    public ResponseEntity createOrUpdatePod(Map<String, Object> metadata) {
        return postUtil.applyPod(metadata);
    }
}
