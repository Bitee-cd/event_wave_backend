package com.bitee.event.Otp;

import com.bitee.event.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping(path = "otp")
public interface OtpController {

    @PostMapping(path = "regenerate")
    ResponseEntity<ApiResponse<String>> regenerateOtp(@Valid @RequestBody RegenerateOtpRequestDto otpRequest);

    @PostMapping(path = "verify")
    ResponseEntity<ApiResponse<String>> verifyOtp(@Valid @RequestBody OtpRequestDto otpRequestDto);
}
