package com.example.Netflix.Accesseror.Model.User;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {
    private String userID;
    private String name;
    private String email;
    private String password;
    private String phoneNo;
    private UserState state;
    private UserRole role;
    private VerificationStatus emailStatus;
    private VerificationStatus phoneStatus;
}
