package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.jwt.TokenProvider;

@RestController
@RequiredArgsConstructor
public class HiController {

    private final TokenProvider tokenProvider;

    @GetMapping("/hi")
    public ResponseEntity<String> sayHi(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.substring(7);

        if (tokenProvider.validateToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            String username = authentication.getName();
            return ResponseEntity.ok("Hi, " + username + "!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}