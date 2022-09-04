package com.example.Netflix.Accesseror.Model.Profile;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Getter
@Builder
public class ProfileDto {

    private String profileId;
    private String name;
    private ProfileType type;
    private Date createdAt;
    private String userId;

}
