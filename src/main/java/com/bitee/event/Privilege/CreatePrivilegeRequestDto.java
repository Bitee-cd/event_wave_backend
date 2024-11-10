package com.bitee.event.Privilege;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePrivilegeRequestDto {

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "User ID is required")
    private Long userid;

    @NotNull(message = "Role ID is required")
    private Long roleid;
}