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
public class RequestMoreInforDelegate implements JavaDelegate {

    private static Logger LOGGER = LoggerFactory.getLogger(RequestMoreInforDelegate.class);

    @Autowired
    private NotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("RequestMoreInforDelegate ..");

        NotificationMessage message = new NotificationMessage();
        message.setToEmail((String) delegateExecution.getVariable("emailAddress"));
        message.setSubject("Information Request");
        message.setToFrom("DoNotReply@doaim.com");
        message.setBody("You are requested to provide more information");

        if(delegateExecution.getVariable("numberOfRequestForMoreInfor") ==  null) {
            delegateExecution.setVariable("numberOfRequestForMoreInfor", 1);
        } else {
            int numberOfRequestForMoreInfor = (int)delegateExecution.getVariable("numberOfRequestForMoreInfor");
            delegateExecution.setVariable("numberOfRequestForMoreInfor", numberOfRequestForMoreInfor+1);
        }

        notificationService.sendMessage(message);

    }
}
