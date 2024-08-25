package com.bitee.event.Email;

import org.springframework.stereotype.Service;


public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
