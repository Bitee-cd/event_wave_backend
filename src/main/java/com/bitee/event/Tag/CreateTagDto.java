package com.bitee.event.Tag;

import com.bitee.event.utils.EnumValid;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTagDto {

    @NotBlank(message="tag name is required")
    String name;

//    TODO confirm this later
//    @EnumValid(enumClass=TagCategory.class)
    @NotNull(message = "tag category is required")
    TagCategory tagCategory;
}
