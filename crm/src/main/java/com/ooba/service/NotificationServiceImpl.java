package com.ooba.service;

import com.ooba.model.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by cleophas on 2018/10/21.
 */

@Component
public class NotificationServiceImpl implements NotificationService {

    private static Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Override
    public void sendMessage(NotificationMessage notificationMessage) {

        LOGGER.info("notifyLead .. " + notificationMessage.getBody() );

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message);
            helper.setTo(notificationMessage.getToEmail());
            helper.setSubject(notificationMessage.getSubject());
            String content = mailContentBuilder.build(notificationMessage.getBody(), notificationMessage.getSubject());
            helper.setText(content);
        } catch (MessagingException e) {
            LOGGER.error("Error .. " + e );
        }
        emailSender.send(message);
    }
}
