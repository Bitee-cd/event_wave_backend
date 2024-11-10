package com.bitee.event.User;

import com.bitee.event.Otp.RegenerateOtpRequestDto;
import com.bitee.event.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserControllerImpl implements UserController{
    @Autowired
    UserService userService;


    @Override
    public ResponseEntity<ApiResponse<User>> signup(UserRequestDto userRequestDto) {
        return userService.signup(userRequestDto);
    }

    @Override
    public ResponseEntity<ApiResponse<Map<String, String>>> login(LoginRequestDto loginRequestDto) {return userService.login(loginRequestDto);}

    @Override
    public ResponseEntity<ApiResponse<String>> forgotPassword(RegenerateOtpRequestDto otpRequest) {
        return userService.forgotPassword(otpRequest);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordRequestDto changePasswordRequest) {
        return userService.changePassword(changePasswordRequest);
    }

    @Override
    public ResponseEntity<ApiResponse<Map<String, List<Map<String,String>>>>> options() {
        return userService.options();
    }
}
