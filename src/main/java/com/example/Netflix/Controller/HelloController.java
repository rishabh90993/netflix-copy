package com.example.Netflix.Controller;

import com.example.Netflix.Security.Roles;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Role;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @Secured({Roles.User,Roles.Customer})
    public String hello(){
        return "Hello from the other side.";
    }

}
