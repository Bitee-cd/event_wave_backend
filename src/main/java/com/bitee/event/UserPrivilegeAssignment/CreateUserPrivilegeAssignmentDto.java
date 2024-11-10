package com.bitee.event.UserPrivilegeAssignment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserPrivilegeAssignmentDto {


    @NotNull(message = "User ID is required")
    private Long userid;

    @NotNull(message = "Privilege ID is required")
    private Long privilege;

}
