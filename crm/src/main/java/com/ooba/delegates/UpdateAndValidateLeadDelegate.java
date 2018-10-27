package com.ooba.delegates;

import com.ooba.model.Lead;
import com.ooba.service.LeadService;
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
public class UpdateAndValidateLeadDelegate implements JavaDelegate {

    private static Logger LOGGER = LoggerFactory.getLogger(UpdateAndValidateLeadDelegate.class);

    @Autowired
    private LeadService leadService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("UpdateAndValidateLeadDelegate .." + (Long) delegateExecution.getVariable("id"));

        Lead lead = leadService.getLead((Long) delegateExecution.getVariable("id"));
        lead.setFirstName((String) delegateExecution.getVariable("firstName"));
        lead.setLastame((String) delegateExecution.getVariable("lastName"));
        lead.setEmailAddres((String) delegateExecution.getVariable("emailAddress"));
        lead.setAddress((String) delegateExecution.getVariable("address"));
        lead.setNotes((String) delegateExecution.getVariable("notes"));

        leadService.update(lead);
        boolean isValid =  leadService.getIsValid(lead);
        delegateExecution.setVariable("isValid", isValid);
    }
}
