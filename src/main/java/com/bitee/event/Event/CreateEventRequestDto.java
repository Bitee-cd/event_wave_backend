package com.bitee.event.Event;

import com.bitee.event.Tag.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDto {

    @NotNull(message = "Event type is required")
    private EventType type;

    @NotBlank(message = "location is required")
    private String location;


    private String address;


    private String meetingLink;

    @Pattern(
            regexp = "^(https://.*)?$",
            message = "Image URL must start with 'https://' if provided"
    )
    private String image;

    @Column(name = "description")
    private String description;

    @Digits(integer = 10, fraction = 2, message = "Price must be a numeric value with up to 10 digits and 2 decimal places")
    private BigDecimal price;


    @NotNull(message = "Start Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startDate;

    @NotNull(message = "End Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endDate;

    @NotBlank(message="Tags is required")
    private List<Tag> tags;
}
