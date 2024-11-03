package com.bitee.event.dao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Email is required")
    @Email(message="Email should be valid")
    private String email;

    @NotBlank(message = "Otp is required")
    @Size(min=6,max=6,message = "Otp must be 6 digits")
    private String otp;

    @NotBlank(message="Password is required")
    @Size(min = 6,message = "Password must be at least 6 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$",
            message = "Password must contain at least one letter, one number, and one special character"
    )
    private String Password;
}
