package com.bitee.event.User;

import com.bitee.event.Otp.RegenerateOtpRequestDto;
import com.bitee.event.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "auth")
public interface UserController {


    @PostMapping(path="signup")
    ResponseEntity<ApiResponse<User>> signup(@Valid @RequestBody UserRequestDto userRequestDto);

    @PostMapping(path="login")
    ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginRequestDto loginRequestDto);

    @PostMapping(path="password/forgot")
    ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody RegenerateOtpRequestDto otpRequest);

    @PostMapping(path="password/change")
    ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequest);

    @GetMapping(path= "options")
    ResponseEntity<ApiResponse<Map<String, List<Map<String,String>>>>> options();
}
