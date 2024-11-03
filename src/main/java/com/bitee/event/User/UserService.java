package com.bitee.event.User;

import com.bitee.event.dao.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    ResponseEntity<ApiResponse<User>> signup(@RequestBody UserRequest userRequest);

    ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody LoginRequest loginRequest);

    ResponseEntity<ApiResponse<String>>  forgotPassword(@RequestBody RegenerateOtp otpRequest);

    ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest);

    ResponseEntity<ApiResponse<Map<String, List<Map<String,String>>>>> options();
}
