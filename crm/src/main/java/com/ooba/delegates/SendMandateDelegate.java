package com.ooba.delegates;

import com.ooba.model.Lead;
import com.ooba.model.LeadStatus;
import com.ooba.model.NotificationMessage;
import com.ooba.service.DelegateHelperServiceImp;
import com.ooba.service.LeadService;
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

    private NotificationService notificationService;

    private DelegateHelperServiceImp delegateHelper;

    @Autowired
    public SendMandateDelegate(DelegateHelperServiceImp delegateHelper, NotificationService notificationService) {
        this.delegateHelper = delegateHelper;
        this.notificationService = notificationService;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("SendMandateDelegate ..");

        delegateExecution.setVariable("status", LeadStatus.MandateSent);
        delegateHelper.updateLeadFromDelegate(delegateExecution);

        NotificationMessage message = new NotificationMessage();
        message.setToEmail((String) delegateExecution.getVariable("emailAddress"));
        message.setSubject("Mandate Information");
        message.setToFrom("DoNotReply@doaim.com");
        message.setBody("Mandate Information");
        message.setAction("MandateInformation");
        message.setActionDescription("Mandate Information");
        message.setLeadId((Long) delegateExecution.getVariable("id"));

        notificationService.sendMessage(message);

    }
}
