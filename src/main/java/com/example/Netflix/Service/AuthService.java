package com.example.Netflix.Service;

import com.example.Netflix.Accesseror.AuthAccessor;
import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Accesseror.UserAccessor;
import com.example.Netflix.Exceptions.AuthenticationFaliureException;
import com.example.Netflix.Exceptions.DependencyFaliureException;
import com.example.Netflix.Security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private AuthAccessor authAccessor;

    @Autowired
    private UserAccessor userAccessor;


    /**
     *
     *
     * @param email email of the user who wants to login
     * @param password password assosiated with that email
     * @return UserDto if Authentication is successfull otherwise throws Authentication faliure Exception
     */

    public String login(String email, String password){
      UserDto user =  userAccessor.getUserByEmail(email);

          if (user.getPassword().equals(password)) {
              String token = Jwts.builder().
                      setSubject(email).
                      setAudience(user.getRole().name())
                      .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                      .signWith(SignatureAlgorithm.HS512, SecurityConstants.secretKey.getBytes())
                      .compact();
              authAccessor.storeToken(user.getUserID(), token);
              return token;
          }
          throw new AuthenticationFaliureException("Email or Password is incorrect");

    }

    /***
     *
     * @param token
     * Loggs User out if token is present else sends Bad Request as Response
     */
    public void logout(String token){
        authAccessor.deleteByToken(token);
    }


}
