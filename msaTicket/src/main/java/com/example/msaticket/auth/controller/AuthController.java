package com.example.msaticket.auth.controller;

import com.example.msaticket.auth.dto.MemberResponseDto;
import com.example.msaticket.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyDetails(@RequestHeader("Authorization") String bearerToken) {
        String token = resolveToken(bearerToken);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        MemberResponseDto memberResponseDto = authService.getUserInfoFromToken(token);
        return ResponseEntity.ok(memberResponseDto);
    }
    private String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
