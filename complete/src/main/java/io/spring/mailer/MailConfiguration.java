package io.spring.mailer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfiguration {

    @Bean
    public Mailer mailer(JavaMailSender javaMailSender) {
        return new Mailer(javaMailSender);
    }

    @Bean
    public ApplicationRunner applicationRunner (Mailer mailer) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                mailer.sendEmail();
            }
        };
    }
}
