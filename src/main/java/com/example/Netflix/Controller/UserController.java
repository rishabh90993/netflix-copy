package com.example.Netflix.Controller;

import com.example.Netflix.Accesseror.AuthAccessor;
import com.example.Netflix.Accesseror.Model.Otp.OtpRequest;
import com.example.Netflix.Exceptions.InvalidDataException;
import com.example.Netflix.Security.Roles;
import com.example.Netflix.Service.AuthService;
import com.example.Netflix.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/logoutUser")
    @Secured({Roles.Customer,Roles.User})
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authToken){

           String token = authToken.replaceFirst("Bearer ","");

            if(token == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token not found.");
            else authService.logout(token);

            return ResponseEntity.status(HttpStatus.OK).body("User Logged out successfully.");
    }

    @PostMapping("/subscription")
    @Secured({Roles.User})
    public ResponseEntity<String> activateSubscription(){
        userService.activateSubscription();
        return  new ResponseEntity<String>("User Subscription activated.", HttpStatus.CREATED);
    }

    @DeleteMapping("/subscription")
    @Secured({Roles.Customer})
    public ResponseEntity<String> deActivateSubscription(){
        userService.deActivateSubscription();
        return  new ResponseEntity<String>("User Subscription de-activated.", HttpStatus.CREATED);
    }

    @PostMapping("/email")
    @Secured({Roles.User,Roles.Customer})
    public ResponseEntity<String> verifyEmail(@RequestBody OtpRequest otpRequest){
        try{
        userService.verifyEmail(otpRequest.getOtp());
        return  new ResponseEntity<String>("Email verified SuccessFully.", HttpStatus.OK);

        }catch (InvalidDataException e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/phone")
    @Secured({Roles.User,Roles.Customer})
    public ResponseEntity<String> verifyPhone(@RequestBody OtpRequest otpRequest){
        try{
            userService.verifyPhone(otpRequest.getOtp());
            return  new ResponseEntity<String>("Phone verified SuccessFully.", HttpStatus.OK);

        }catch (InvalidDataException e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
