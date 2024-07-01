package com.example.demo.controller;

import com.example.demo.dto.PodDto;
import com.example.demo.service.GetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.demo.util.GetUtil.RESOURCE_TYPE_POD;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final GetService getService;

    @GetMapping("/pods")
    public List<PodDto> getPods(){
        return getService.getResource(RESOURCE_TYPE_POD);
    }

}