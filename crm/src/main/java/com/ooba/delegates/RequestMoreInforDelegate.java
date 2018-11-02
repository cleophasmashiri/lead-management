package com.ooba.delegates;

import com.ooba.model.LeadStatus;
import com.ooba.model.NotificationMessage;
import com.ooba.service.DelegateHelperServiceImp;
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

    private NotificationService notificationService;

    private DelegateHelperServiceImp delegateHelper;

    @Autowired
    public RequestMoreInforDelegate(DelegateHelperServiceImp delegateHelper, NotificationService notificationService) {
        this.delegateHelper = delegateHelper;
        this.notificationService = notificationService;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("RequestMoreInforDelegate ..");

        if(delegateExecution.getVariable("numberOfRequestForMoreInfor") ==  null) {
            delegateExecution.setVariable("numberOfRequestForMoreInfor", 1);
        } else {
            int numberOfRequestForMoreInfor = (int)delegateExecution.getVariable("numberOfRequestForMoreInfor");
            delegateExecution.setVariable("numberOfRequestForMoreInfor", numberOfRequestForMoreInfor+1);
        }

        delegateExecution.setVariable("status", LeadStatus.RequestedMoreInformation);
        delegateHelper.updateLeadFromDelegate(delegateExecution);

        NotificationMessage message = new NotificationMessage();
        message.setToEmail((String) delegateExecution.getVariable("emailAddress"));
        message.setSubject("Information Request");
        message.setToFrom("DoNotReply@doaim.com");
        message.setBody("You are requested to provide more information");
        message.setAction("updateInformation");
        message.setActionDescription("Update Information");
        notificationService.sendMessage(message);

    }
}
