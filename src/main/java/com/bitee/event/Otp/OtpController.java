package com.bitee.event.Otp;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.OtpRequest;
import com.bitee.event.dao.RegenerateOtp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping(path = "api/v1/otp")
public interface OtpController {

    @PostMapping(path = "regenerate")
    ResponseEntity<ApiResponse<String>> regenerateOtp(@Valid @RequestBody RegenerateOtp otpRequest);

    @PostMapping(path = "verify")
    ResponseEntity<ApiResponse<String>> verifyOtp(@Valid @RequestBody OtpRequest otpRequest);
}
