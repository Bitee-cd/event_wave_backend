package com.bitee.event.Otp;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.OtpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OtpService {
    ResponseEntity<ApiResponse<String>> verifyOtp(OtpRequest otpRequest);

    ResponseEntity<ApiResponse<String>> regenerateOtp(String email);
    ResponseEntity<ApiResponse<String>> generateOtp(String email);
}
