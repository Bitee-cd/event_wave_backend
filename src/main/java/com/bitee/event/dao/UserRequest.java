package com.bitee.event.dao;


import com.bitee.event.User.AccountStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "First Name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    private String otherNames;
    private String address;
    @NotBlank(message="Email is required")
    @Email(message="Email is not valid")
    private String email;
    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;
    @NotBlank(message="Password is required")
    @Size(min = 6,message = "Password must be at least 6 characters long")
    private String Password;
}

