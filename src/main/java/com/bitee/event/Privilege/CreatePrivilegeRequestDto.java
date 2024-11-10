package com.bitee.event.Privilege;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePrivilegeRequestDto {
    @NotBlank(message = "data is required")
    @Pattern(regexp = "^[A-Z]+(_[A-Z]+)*$", message = "data must be uppercase with words separated by underscores (e.g., VIEW_USERS)")
    private String data;

    @NotBlank(message = "Description is required")
    private String description;


    @NotNull(message = "Role ID is required")
    private Long roleid;
}