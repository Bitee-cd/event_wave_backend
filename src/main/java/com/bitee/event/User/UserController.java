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

@RequestMapping(path = "api/v1")
public interface UserController {

    @GetMapping(path="hello")
    ResponseEntity<ApiResponse<String>> helloWorld();

    @PostMapping(path="auth/signup")
    ResponseEntity<ApiResponse<User>> signup(@Valid @RequestBody UserRequest userRequest);

    @PostMapping(path="auth/login")
    ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping(path="auth/password/forgot")
    ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody RegenerateOtp otpRequest);

    @PostMapping(path="auth/password/change")
    ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest);

    @GetMapping(path= "options")
    ResponseEntity<ApiResponse<Map<String, List<Map<String,String>>>>> options();
}
