package com.example.msaticket.service;


import com.example.msaticket.dto.MemberResponseDto;
import com.example.msaticket.entity.Member;
import com.example.msaticket.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    public MemberResponseDto getUserInfoFromToken(String token) {
        if (!tokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid Token");
        }

        Authentication authentication = tokenProvider.getAuthentication(token);
        Long memberId = Long.parseLong(authentication.getName());
        Member member = customUserDetailsService.loadUserByMemberId(memberId);
        return MemberResponseDto.of(member);
    }
}
