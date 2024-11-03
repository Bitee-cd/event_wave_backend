package com.bitee.event.Otp;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.OtpRequest;
import com.bitee.event.dao.RegenerateOtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpControllerImpl implements OtpController{
    @Autowired
    OtpService otpService;
    @Override
    public ResponseEntity<ApiResponse<String>> regenerateOtp(RegenerateOtp otpRequest) {
        return otpService.regenerateOtp(otpRequest.getEmail());
    }

    @Override
    public ResponseEntity<ApiResponse<String>> verifyOtp(OtpRequest otpRequest) {
        return otpService.verifyOtp(otpRequest);
    }
}
