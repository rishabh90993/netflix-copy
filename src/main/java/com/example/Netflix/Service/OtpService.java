package com.example.Netflix.Service;

import com.example.Netflix.Accesseror.Model.Otp.OtpSentTo;
import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Accesseror.OtpAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Autowired
    OtpAccessor otpAccessor;

    public void generateOtp(OtpSentTo otpSentTo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = (UserDto) authentication.getPrincipal();

        otpAccessor.generateOtp(user.getUserID(),otpSentTo);

    }

}
