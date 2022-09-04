package com.example.Netflix.Controller;

import com.example.Netflix.Accesseror.Model.Otp.OtpSentTo;
import com.example.Netflix.Accesseror.OtpAccessor;
import com.example.Netflix.Security.Roles;
import com.example.Netflix.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class OtpController {

    @Autowired
    OtpService otpService;

    @PostMapping("user/otp")
    @Secured({Roles.Customer,Roles.User})
    public ResponseEntity<String> generateOtp(){
        try{
            otpService.generateOtp(OtpSentTo.PHONE);
            return new ResponseEntity<String>("otp created Successfully", HttpStatus.OK);
        }catch (RuntimeException e ){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
