package com.bitee.event.Event;

import com.bitee.event.Tag.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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


    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
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
