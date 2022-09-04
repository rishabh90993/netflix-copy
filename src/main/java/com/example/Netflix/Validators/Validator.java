package com.example.Netflix.Validators;

import com.example.Netflix.Accesseror.Model.Profile.ProfileDto;
import com.example.Netflix.Accesseror.ProfileAccessor;
import com.example.Netflix.Exceptions.InvalidProfileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    @Autowired
    ProfileAccessor profileAccessor;

    public void validateProfile(final String profileId,final String userId){
        ProfileDto profileDto = profileAccessor.getProfile(profileId);
        if(profileDto !=null && profileDto.getUserId().equals(userId)){
            return;
        }
        throw new InvalidProfileException("Profile "+profileId + " doesnot sxists");
    }


}
