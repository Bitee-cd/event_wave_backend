package com.bitee.event.User;

import com.bitee.event.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserControllerImpl implements UserController{
    @Autowired
    UserService userService;


    @Override
    public ResponseEntity<ApiResponse<User>> signup(UserRequest userRequest) {
        return userService.signup(userRequest);
    }

    @Override
    public ResponseEntity<ApiResponse<Map<String, String>>> login(LoginRequest loginRequest) {return userService.login(loginRequest);}

    @Override
    public ResponseEntity<ApiResponse<String>> forgotPassword(RegenerateOtp otpRequest) {
        return userService.forgotPassword(otpRequest);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(changePasswordRequest);
    }

    @Override
    public ResponseEntity<ApiResponse<Map<String, List<Map<String,String>>>>> options() {
        return userService.options();
    }
}
