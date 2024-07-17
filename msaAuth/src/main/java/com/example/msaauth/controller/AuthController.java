package com.example.msaauth.controller;

import com.example.msaauth.dto.MemberRequestDto;
import com.example.msaauth.dto.MemberResponseDto;
import com.example.msaauth.dto.TokenDto;
import com.example.msaauth.dto.TokenRequestDto;
import com.example.msaauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody MemberRequestDto memberRequestDto) {
        authService.logout(memberRequestDto);
        String responseMessage = memberRequestDto.getEmail() + " logout 완료";
        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

}