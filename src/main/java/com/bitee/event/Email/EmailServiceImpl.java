package com.bitee.event.Email;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${mailtrap.smtp.host}")
    private String host;

    @Value("${mailtrap.smtp.port}")
    private String port;

    @Value("${mailtrap.smtp.username}")
    private String username;

    @Value("${mailtrap.smtp.password}")
    private String password;


    @Value("${mailtrap.smtp.sender}")
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
            mailMessage.setText(emailDetailsDto.getMessageBody());
            javaMailSender.send(message);
            System.out.println("Mail message sent successfully");

        }catch(Exception e){
            System.out.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }

    }


    @Override
    public void sendJakartaMail(EmailDetailsDto emailDetailsDto) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        System.out.println("host: "+host+" port: "+port+" username: "+username+" password: "+password+" senderEmail: "+senderEmail);
        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(emailDetailsDto.getRecipient())
            );
            message.setSubject(emailDetailsDto.getSubject());

            message.setContent(
                    emailDetailsDto.getMessageBody(),
                    "text/html; charset=utf-8"
            );

            Transport.send(message);
            System.out.println("Email sent successfully to: {} " +emailDetailsDto.getRecipient());

        } catch (MessagingException e) {
            System.out.println("Failed to send email: {} "+ e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
