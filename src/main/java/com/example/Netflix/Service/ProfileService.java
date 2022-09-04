package com.example.Netflix.Service;

import com.example.Netflix.Accesseror.Model.Profile.ProfileDto;
import com.example.Netflix.Accesseror.Model.Profile.ProfileType;
import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Accesseror.ProfileAccessor;
import com.example.Netflix.Exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    ProfileAccessor profileAccessor;

    public void addProfile(String name , ProfileType type){
        if(name.length() < 5 || name.length()>20)
         throw new InvalidDataException("Profile name should be between 5 and 20 length.");

        UserDto user = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileAccessor.addProfile(user.getUserID(), name,type);
    }

    public void deActivateProfile(String profileId){
        profileAccessor.deActivateProfile(profileId);
    }

}
