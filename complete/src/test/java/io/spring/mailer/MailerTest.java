package io.spring.mailer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import java.io.IOException;

public class MailerTest {


    @Test
    public void testMe() throws MessagingException, IOException {
        JavaMailSender javaMailSender = Mockito.mock(JavaMailSender.class);
        Mailer mailer = new Mailer(javaMailSender);
        mailer.sendEmail();

    }
}
