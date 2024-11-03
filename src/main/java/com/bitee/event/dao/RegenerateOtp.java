package com.bitee.event.dao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegenerateOtp{

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

}