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
public class CreateLeadDelegate implements JavaDelegate {

    private static Logger LOGGER = LoggerFactory.getLogger(CreateLeadDelegate.class);

    @Autowired
    private LeadService leadService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("Creating a new lead ..");

        Lead lead = new Lead();
        lead.setFirstName((String) delegateExecution.getVariable("firstName"));
        lead.setLastame((String) delegateExecution.getVariable("lastName"));
        lead.setEmailAddres((String) delegateExecution.getVariable("emailAddress"));
        lead.setAddress((String) delegateExecution.getVariable("address"));
        lead.setNotes((String) delegateExecution.getVariable("notes"));

        leadService.createNewLead(lead);

        lead.setId(lead.getId());

        delegateExecution.setVariable("id", lead.getId());
    }
}
