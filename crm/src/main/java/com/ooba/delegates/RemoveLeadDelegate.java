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
public class RemoveLeadDelegate implements JavaDelegate {

    private static Logger LOGGER = LoggerFactory.getLogger(RemoveLeadDelegate.class);

    @Autowired
    private LeadService leadService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("RemoveLeadDelegate .." + (Long) delegateExecution.getVariable("id"));

        leadService.delete((Long) delegateExecution.getVariable("id"));
    }
}
