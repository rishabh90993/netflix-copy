package com.example.Netflix.Security;

import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Accesseror.UserAccessor;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RoleBasedAuthenticationFilter implements AuthenticationManager {

    private final UserAccessor userAccessor;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        List<GrantedAuthority> allowedRoles = new ArrayList<>(authentication.getAuthorities());

        UserDto userDto = userAccessor.getUserByEmail(email);
        if(userDto!=null){
            for(GrantedAuthority authority : allowedRoles){
                if(authority.equals(userDto.getRole().toString())){
                    return  new UsernamePasswordAuthenticationToken( new User(email,userDto.getPassword(),allowedRoles),
                            "",allowedRoles);
                }
            }
        }
        throw new BadCredentialsException("Role not allowed!!");
    }
}
