package com.example.Netflix.Accesseror.Model.Otp;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Builder
@Getter
public class OtpDto {
    private String otpId;
    private String userId;
    private String otp;
    private OtpState state;
    private Date createdAt;
    private OtpSentTo sentTo;
}
