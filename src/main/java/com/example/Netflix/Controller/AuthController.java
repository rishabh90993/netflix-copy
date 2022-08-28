package com.example.Netflix.Controller;

import com.example.Netflix.Controller.Models.LoginUser;
import com.example.Netflix.Exceptions.AuthenticationFaliureException;
import com.example.Netflix.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginUser user){
        try {
            String token = authService.login(user.getEmail(), user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        }
        catch (AuthenticationFaliureException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
