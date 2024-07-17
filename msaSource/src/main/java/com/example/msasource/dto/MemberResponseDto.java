package com.example.msasource.dto;

import com.example.msasource.entity.Member;
import lombok.Data;

@Data
public class MemberResponseDto {
    private Long id;
    private String email;
    private String authority;

    public static MemberResponseDto of(Member member) {
        MemberResponseDto dto = new MemberResponseDto();
        dto.setId(member.getId());
        dto.setEmail(member.getEmail());
        dto.setAuthority(member.getAuthority().toString());
        return dto;
    }
}
