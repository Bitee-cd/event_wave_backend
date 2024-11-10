package com.bitee.event.Tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTagRequestDto {

    @NotBlank(message="tag name is required")
    String name;

//    TODO confirm this later
//    @EnumValid(enumClass=TagCategory.class)
    @NotNull(message = "tag category is required")
    TagCategory tagCategory;
}
