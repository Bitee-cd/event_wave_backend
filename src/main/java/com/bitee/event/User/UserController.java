package com.bitee.event.User;

import com.bitee.event.Otp.RegenerateOtpRequestDto;
import com.bitee.event.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.Multipart;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping()
@Tag(name="User Management APIs")
public interface UserController {


    @PostMapping(path="auth/signup")
    ResponseEntity<ApiResponse<User>> signup(@Valid @RequestBody UserRequestDto userRequestDto);

    @PostMapping(path="auth/login")
    ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginRequestDto loginRequestDto);

    @PostMapping(path="auth/password/forgot")
    ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody RegenerateOtpRequestDto otpRequest);

    @PostMapping(path="auth/password/change")
    ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequest);

    @GetMapping(path= "options")
    ResponseEntity<ApiResponse<Map<String, List<Map<String,String>>>>> options();

    @PostMapping(path= "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ApiResponse<Map<String,String>>> uploadImage( @Parameter(description = "File to upload", required = true) @RequestParam("file") MultipartFile file);
}
