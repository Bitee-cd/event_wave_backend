package com.bitee.event.User;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

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
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$",
            message = "Password must contain at least one letter, one number, and one special character"
    )
    private String Password;
}

