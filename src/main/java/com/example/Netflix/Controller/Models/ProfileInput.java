package com.example.Netflix.Controller.Models;


import com.example.Netflix.Accesseror.Model.Profile.ProfileType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class ProfileInput {
    private String name;
    private ProfileType type;
}
