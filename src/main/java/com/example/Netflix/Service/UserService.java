package com.example.Netflix.Service;

import com.example.Netflix.Accesseror.Model.Otp.OtpDto;
import com.example.Netflix.Accesseror.Model.Otp.OtpSentTo;
import com.example.Netflix.Accesseror.Model.Otp.OtpState;
import com.example.Netflix.Accesseror.Model.User.UserDto;
import com.example.Netflix.Accesseror.Model.User.UserRole;
import com.example.Netflix.Accesseror.Model.User.UserState;
import com.example.Netflix.Accesseror.Model.User.VerificationStatus;
import com.example.Netflix.Accesseror.OtpAccessor;
import com.example.Netflix.Accesseror.UserAccessor;
import com.example.Netflix.Exceptions.InvalidDataException;
import com.example.Netflix.Exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    UserAccessor userAccessor;

    @Autowired
    OtpAccessor otpAccessor;

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

    /***
     * method to activate a subscription
     */

    public void activateSubscription(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = (UserDto) authentication.getPrincipal();
        userAccessor.updateUserRole(user.getUserID(),UserRole.ROLE_CUSTOMER);
    }

    /***
     * method to De Activate a subscription
     */
    public void deActivateSubscription(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = (UserDto) authentication.getPrincipal();
        userAccessor.updateUserRole(user.getUserID(),UserRole.ROLE_USER);
    }


    /***
     *
     * @param otp verify's otp if otp is valid else throws invalid otp exception
     */
    public void verifyEmail(String otp){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = (UserDto) authentication.getPrincipal();

        OtpDto otpDto = otpAccessor.getUnUsedOtp(otp,user.getUserID(), OtpSentTo.EMAIL);
        if(otpDto!=null){
            userAccessor.updateEmailVerificationStatus(user.getUserID(), VerificationStatus.VERIFIED);
            otpAccessor.updateOtpState(otpDto.getOtpId(), OtpState.USED);
        }else{
            throw new InvalidDataException("Otp if not valid.");
        }

    }



    /***
     *
     * @param otp verify's otp if otp is valid else throws invalid otp exception
     */
    public void verifyPhone(String otp){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = (UserDto) authentication.getPrincipal();

        OtpDto otpDto = otpAccessor.getUnUsedOtp(otp,user.getUserID(), OtpSentTo.PHONE);
        if(otpDto!=null){
            userAccessor.updatePhoneVerificationStatus(user.getUserID(), VerificationStatus.VERIFIED);
            otpAccessor.updateOtpState(otpDto.getOtpId(), OtpState.USED);
        }else{
            throw new InvalidDataException("Otp if not valid.");
        }

    }
}
