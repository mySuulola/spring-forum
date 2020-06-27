package com.suulola.forum.service;

import com.suulola.forum.exception.ForumCustomException;
import com.suulola.forum.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class MailService {

    final JavaMailSender javaMailSender;

    final MailContentBuilder mailContentBuilder;

    @Autowired
    public MailService(JavaMailSender javaMailSender, MailContentBuilder mailContentBuilder) {
        this.javaMailSender = javaMailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    @Async
    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("admin@forum.com");
            messageHelper.setTo(notificationEmail.getReceipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            javaMailSender.send(messagePreparator);
//            log.info("Activation email sent!!!");
        }catch (MailException e) {
//            log.error(e.getMessage());
            throw new ForumCustomException("Exception occurred when sending mail to " + notificationEmail.getReceipient());
        }

    }
}
