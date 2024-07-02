package com.example.demo.service;

import com.example.demo.util.DeleteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteService {
    private final DeleteUtil deleteUtil;

    public ResponseEntity deleteResource(String resourceName) {
        return deleteUtil.deletePod(resourceName);
    }
}
