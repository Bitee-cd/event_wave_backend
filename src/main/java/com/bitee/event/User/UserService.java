package com.bitee.event.User;

import com.bitee.event.Otp.RegenerateOtpRequestDto;
import com.bitee.event.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    ResponseEntity<ApiResponse<User>> signup(@RequestBody UserRequestDto userRequestDto);

    ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody LoginRequestDto loginRequestDto);

    ResponseEntity<ApiResponse<String>>  forgotPassword(@RequestBody RegenerateOtpRequestDto otpRequest);

    ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequest);

    ResponseEntity<ApiResponse<Map<String, List<Map<String,String>>>>> options();
}
