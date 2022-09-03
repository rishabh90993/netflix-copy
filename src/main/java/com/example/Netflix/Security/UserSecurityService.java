package com.example.Netflix.Security;

import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Accesseror.UserAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    UserAccessor userAccessor;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userAccessor.getUserByEmail(username);
        if(userDto!=null){
            return new User(userDto.getEmail(),userDto.getPassword(), List.of(new SimpleGrantedAuthority(userDto.getRole().name())));
        }
        throw new UsernameNotFoundException("User with the email "+username+" not found.");
    }
}
