package com.bitee.event.Email;


public interface EmailService {
    void sendEmailAlert(EmailDetailsDto emailDetailsDto);
    void sendJakartaMail(EmailDetailsDto emailDetailsDto);
}
