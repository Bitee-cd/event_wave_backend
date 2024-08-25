package com.bitee.event.User;

import com.bitee.event.Email.EmailDetails;
import com.bitee.event.Email.EmailService;
import com.bitee.event.Otp.Otp;
import com.bitee.event.Otp.OtpRepository;
import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.UserRequest;
import com.bitee.event.utils.EventUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OtpRepository otpRepository;

    @Autowired
    EmailService emailService;

    /**
     * create a user
     * check if a user has an account
     * sends otp to user email for activation
     * @param userRequest
     * @return api response
     */
    @Override
    public ResponseEntity<ApiResponse<User>> signup(UserRequest userRequest) {

//        TODO
//          @validate password to be alphanumberic
        //  @validate phone number
        if (userRepository.existsByEmail(userRequest.getEmail())){
            ApiResponse<User> accountExists = ApiResponse.error("409","User with provided email already exists",
                    null);
            return new ResponseEntity<>(accountExists, HttpStatus.CONFLICT);
        }
        User newUser = getNewUser(userRequest);
        userRepository.save(newUser);
        //generate Random Otp
        Otp otp = new Otp();
        otp.setCreatedAt(new Date());
        otp.setUser(newUser);
        otp.setExpiresAt(new Date(System.currentTimeMillis() + 15 *60*1000)); //15 minutes from now
        otp.setToken(EventUtils.generateRandomToken());
        otpRepository.save(otp);
        //send Otp to user

        EmailDetails signUpUserEmail = new EmailDetails();
        signUpUserEmail.setRecipient(newUser.getEmail());
        signUpUserEmail.setSubject("OTP Token for Event Wave Registration");
        signUpUserEmail.setMessageBody(EventUtils.EmailOtpBody(userRequest.getFirstName(),otp.getToken()));
        emailService.sendEmailAlert(signUpUserEmail);


        ApiResponse<User> otpSent = ApiResponse.success("201","OTp sent to email for verification",null);
        return new ResponseEntity<>(otpSent,HttpStatus.CREATED);
    }

    private static User getNewUser(UserRequest userRequest) {
        User newUser = new User();
        newUser.setEmail(userRequest.getEmail());
        newUser.setRole(UserRole.USER);
        newUser.setAddress(userRequest.getAddress());
        newUser.setStatus(AccountStatus.INACTIVE);
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        newUser.setOtherNames(userRequest.getOtherNames());
        newUser.setPhoneNumber(userRequest.getPhoneNumber());
        newUser.setPassword(userRequest.getPassword());
        return newUser;
    }
}
