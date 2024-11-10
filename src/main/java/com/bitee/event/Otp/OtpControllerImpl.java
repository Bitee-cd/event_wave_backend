package com.bitee.event.Otp;

import com.bitee.event.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpControllerImpl implements OtpController{
    @Autowired
    OtpService otpService;
    @Override
    public ResponseEntity<ApiResponse<String>> regenerateOtp(RegenerateOtpRequestDto otpRequest) {
        return otpService.regenerateOtp(otpRequest.getEmail());
    }

    @Override
    public ResponseEntity<ApiResponse<String>> verifyOtp(OtpRequestDto otpRequestDto) {
        return otpService.verifyOtp(otpRequestDto);
    }
}
