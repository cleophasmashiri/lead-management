package com.ooba.delegates;

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
public class CheckDuplicateLeadsDelegate implements JavaDelegate {

    private static Logger LOGGER = LoggerFactory.getLogger(CheckDuplicateLeadsDelegate.class);

    @Autowired
    private LeadService leadService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("Checking for duplicate lead ..");

        boolean hasDuplicate = leadService.checkDuplicate((String) delegateExecution.getVariable("email"));

        delegateExecution.setVariable("isDuplicate", hasDuplicate);
    }
}
