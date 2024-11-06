package com.bitee.event.User;

import com.bitee.event.dao.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "api/v1/auth")
public interface UserController {


    @PostMapping(path="signup")
    ResponseEntity<ApiResponse<User>> signup(@Valid @RequestBody UserRequest userRequest);

    @PostMapping(path="login")
    ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping(path="password/forgot")
    ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody RegenerateOtp otpRequest);

    @PostMapping(path="password/change")
    ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest);

    @GetMapping(path= "options")
    ResponseEntity<ApiResponse<Map<String, List<Map<String,String>>>>> options();
}
