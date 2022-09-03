package com.example.Netflix.Controller;

import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Controller.Models.LoginUser;
import com.example.Netflix.Exceptions.AuthenticationFaliureException;
import com.example.Netflix.Exceptions.InvalidDataException;
import com.example.Netflix.Exceptions.UserAlreadyExistsException;
import com.example.Netflix.Service.AuthService;
import com.example.Netflix.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

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

    @PostMapping("/signUp")
    ResponseEntity<String> signup(@RequestBody UserDto user){
        try {
            UserDto userdto = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Created Sucessfully.");
        }
        catch (InvalidDataException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
