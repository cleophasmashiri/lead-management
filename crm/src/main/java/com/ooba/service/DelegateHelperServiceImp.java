package com.ooba.service;

import com.ooba.model.Lead;
import com.ooba.model.LeadStatus;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cleophas on 2018/10/27.
 */
@Service
public class DelegateHelperServiceImp implements DelegateHelperService {


    private LeadService leadService;

    @Autowired
    public DelegateHelperServiceImp(LeadService leadService) {
        this.leadService = leadService;
    }

    @Override
    public void updateLeadFromDelegate(DelegateExecution delegateExecution) {

        Lead lead;

        Long id = (Long) delegateExecution.getVariable("id");

        if (id == null || id < 1) {
            lead = new Lead();
        } else {
            lead = leadService.getLead(id);
        }
        lead.setFirstName((String) delegateExecution.getVariable("firstName"));
        lead.setLastName((String) delegateExecution.getVariable("lastName"));
        lead.setEmailAddress((String) delegateExecution.getVariable("emailAddress"));
        lead.setAddress((String) delegateExecution.getVariable("address"));
        lead.setNotes((String) delegateExecution.getVariable("notes"));
        if (delegateExecution.getVariable("status") != null)
            lead.setStatus((LeadStatus) delegateExecution.getVariable("status"));
        leadService.update(lead);

        if (id == null || id < 1) {
            delegateExecution.setVariable("id", lead.getId());
        }

    }

    @Override
    public boolean getIsValid(Long id) {
        return leadService.getIsValid(id);
    }

    @Override
    public boolean checkDuplicate(String email) {
        return leadService.checkDuplicate(email);
    }
}
