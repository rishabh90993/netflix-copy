package com.example.Netflix.Controller;

import com.example.Netflix.Security.Roles;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @PostMapping("/hello")
    @Secured({Roles.User,Roles.Customer})
    public String hello(){
        return "Hello from the other side.";
    }

}
