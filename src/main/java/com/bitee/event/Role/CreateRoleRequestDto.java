package com.bitee.event.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleRequestDto {

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "data is required")
    @Pattern(regexp = "^[A-Z]+(_[A-Z]+)*$", message = "data must be uppercase with words separated by underscores (e.g., VIEW_USERS)")
    private String data;

}
