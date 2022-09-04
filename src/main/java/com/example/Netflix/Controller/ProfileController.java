package com.example.Netflix.Controller;

import com.example.Netflix.Accesseror.Model.Profile.ProfileDto;
import com.example.Netflix.Controller.Models.ProfileInput;
import com.example.Netflix.Security.Roles;
import com.example.Netflix.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("customer/profile")
    @Secured({Roles.Customer})
    public ResponseEntity<String> addProfile(@RequestBody ProfileInput profileInput){
        try{
            profileService.addProfile(profileInput.getName(),profileInput.getType());
            return new ResponseEntity<String>("Profile added Successfully", HttpStatus.OK);
          }catch (RuntimeException e ){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("customer/profile/{profileId}")
    @Secured({Roles.Customer})
    public ResponseEntity<String> addProfile(@PathVariable("profileId") String profileId){
        try{
            profileService.deActivateProfile(profileId);
            return new ResponseEntity<String>("Profile deleted Successfully", HttpStatus.OK);
        }catch (RuntimeException e ){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
