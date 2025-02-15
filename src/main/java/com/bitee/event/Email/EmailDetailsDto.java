package com.bitee.event.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetailsDto {
    public String recipient;
    private String messageBody;
    private String subject;
}
