package com.example.Netflix.Service;

import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Accesseror.Model.User.UserRole;
import com.example.Netflix.Accesseror.Model.User.UserState;
import com.example.Netflix.Accesseror.UserAccessor;
import com.example.Netflix.Exceptions.InvalidDataException;
import com.example.Netflix.Exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    UserAccessor userAccessor;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    /***
     *
     * @param user gets a User Dto object with user Details
     * @return if Users Details are Valid then it will send success else it will throw an Exception
     */
    public UserDto saveUser(UserDto user){
        // validations

        if(!VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail()).matches())
            throw new InvalidDataException("please enter a valid email address");
        if(user.getPassword().length() < 6)
            throw new InvalidDataException("Password must be of atleast 6 Digits");
        if(user.getPhoneNo().length() < 10)
            throw new InvalidDataException("Phone Number must be of atleast 10 Digits");

        UserDto userdto = userAccessor.getUserByEmail(user.getEmail());
        if(userdto!=null)
        throw new UserAlreadyExistsException("User with the same email already exists.");

        userdto = userAccessor.getUserByPhoneNo(user.getPhoneNo());
        if(userdto!=null)
            throw new UserAlreadyExistsException("User with the same Phone Number already exists.");

        user.setRole(UserRole.ROLE_USER);
        user.setState(UserState.ACTIVE);
        String uuid = UUID.randomUUID().toString();
        user.setUserID(uuid);

        return userAccessor.addUser(user);
    }

}
