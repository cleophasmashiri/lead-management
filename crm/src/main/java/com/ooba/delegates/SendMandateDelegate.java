package com.ooba.delegates;

import com.ooba.model.NotificationMessage;
import com.ooba.service.NotificationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cleophas on 2018/10/17.
 */

@Component
public class SendMandateDelegate implements JavaDelegate {

    private static Logger LOGGER = LoggerFactory.getLogger(SendMandateDelegate.class);

    @Autowired
    private NotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("SendMandateDelegate ..");

        NotificationMessage message = new NotificationMessage();
        message.setToEmail((String) delegateExecution.getVariable("emailAddress"));
        message.setSubject("Mandate Information");
        message.setToFrom("DoNotReply@doaim.com");
        message.setBody("Mandate Information");

        notificationService.sendMessage(message);

    }
}
