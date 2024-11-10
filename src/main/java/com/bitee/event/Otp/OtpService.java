package com.bitee.event.Otp;

import com.bitee.event.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OtpService {
    ResponseEntity<ApiResponse<String>> verifyOtp(OtpRequestDto otpRequestDto);

    ResponseEntity<ApiResponse<String>> regenerateOtp(String email);
    ResponseEntity<ApiResponse<String>> generateOtp(String email);


}
