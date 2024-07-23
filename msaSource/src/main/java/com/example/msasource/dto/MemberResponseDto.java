package com.example.msasource.dto;

import com.example.msasource.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String email;
    private String authority;

    public static MemberResponseDto of(Long memberId, String email, String authority) {
        return new MemberResponseDto(memberId, email, authority);
    }
}