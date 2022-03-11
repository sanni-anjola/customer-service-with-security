package io.anjola.customerservicejava.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import javax.mail.internet.MimeMessage;

import static io.anjola.customerservicejava.util.ApplicationConstants.*;

@Configuration
public class MailConfig {
    private final JavaMailSender javaMailSender;

    public MailConfig(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String to, String subject, String content) throws Exception{
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setFrom(FROM_EMAIL, FROM_NAME);
        messageHelper.setTo(to);
        messageHelper.setSubject(MAIL_SUBJECT.concat(subject));
        messageHelper.setText(content, true);

        javaMailSender.send(mimeMessage);
    }
}
