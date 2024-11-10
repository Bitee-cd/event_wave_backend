package com.bitee.event.Email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender javaMailSender;

    @Value("$spring.mail.sender")
    private String senderEmail;
    @Override
    public void sendEmailAlert(EmailDetailsDto emailDetailsDto) {
        try{
            MimeMessage message  =javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            helper.setFrom(senderEmail);
            helper.setTo(emailDetailsDto.getRecipient());
            helper.setSubject(emailDetailsDto.getSubject());
            helper.setText(emailDetailsDto.getMessageBody(), true);
//            message.setContent(emailDetails.getMessageBody(),"text/html");
            mailMessage.setText(emailDetailsDto.getMessageBody());
            javaMailSender.send(message);
            System.out.println("Mail message sent successfully");

        }catch(Exception e){
            System.out.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }

    }
}
