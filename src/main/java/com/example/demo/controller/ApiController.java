package com.example.demo.controller;

import com.example.demo.dto.PodDto;
import com.example.demo.service.DeleteService;
import com.example.demo.service.GetService;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.demo.util.GetUtil.RESOURCE_TYPE_POD;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final PostService postService;
    private final GetService getService;
    private final DeleteService deleteService;


    @GetMapping("/pods")
    public List<PodDto> getPods(){
        return getService.getResource(RESOURCE_TYPE_POD);
    }

    @DeleteMapping("/pods/{podName}")
    public ResponseEntity deletePod(@PathVariable String podName) {
        ResponseEntity response = deleteService.deleteResource(podName);
        return response;
    }

    @PostMapping("/pods")
    public ResponseEntity applyPod(@RequestBody Map<String, Object> podSpec) {
        return postService.createOrUpdatePod(podSpec);
    }

}