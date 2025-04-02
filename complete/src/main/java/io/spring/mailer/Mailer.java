package io.spring.mailer;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class Mailer {
    private JavaMailSender javaMailSender;

    public Mailer(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    void sendEmail() throws MessagingException, IOException {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("someone@exampld.com");
        msg.setFrom("someoneelse@example.com");

        msg.setSubject("Test from Spring Boot");
        msg.setText("Hello, World!");

        javaMailSender.send(msg);

    }
}
