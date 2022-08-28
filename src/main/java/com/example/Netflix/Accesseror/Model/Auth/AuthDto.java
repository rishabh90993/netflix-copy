package com.example.Netflix.Accesseror.Model.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AuthDto {
    private String authId;
    private String token;
    private String userId;
}
