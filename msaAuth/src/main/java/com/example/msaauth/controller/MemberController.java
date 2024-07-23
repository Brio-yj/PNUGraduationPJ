package com.example.msaauth.controller;

import com.example.msaauth.dto.MemberResponseDto;
import com.example.msaauth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/{email}")
    public ResponseEntity<MemberResponseDto> findMemberInfoByEmail(@PathVariable String email) {
        logger.info("membercontroller");
        return ResponseEntity.ok(memberService.findMemberInfoByEmail(email));
    }
}