package com.bitee.event.Tag;

import com.bitee.event.Tag.TagCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {

    private Long Id;

    private String name;


}
