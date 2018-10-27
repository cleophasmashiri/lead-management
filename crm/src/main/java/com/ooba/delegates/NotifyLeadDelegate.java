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
public class NotifyLeadDelegate implements JavaDelegate {

    private static Logger LOGGER = LoggerFactory.getLogger(NotifyLeadDelegate.class);

    @Autowired
    private NotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("Notifying lead ..");

        NotificationMessage message = new NotificationMessage();
        message.setToEmail((String) delegateExecution.getVariable("emailAddress"));
        message.setSubject("Information Request");
        message.setToFrom("DoNotReply@doaim.com");
        message.setBody("You have been added as a lead");

        if(delegateExecution.getVariable("numberOfRequestForMoreInfor") ==  null) {
            delegateExecution.setVariable("numberOfRequestForMoreInfor", 0);
        }

        notificationService.sendMessage(message);

    }
}
