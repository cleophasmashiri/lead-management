package com.ooba.delegates;

import com.ooba.model.LeadStatus;
import com.ooba.service.DelegateHelperServiceImp;
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

    private DelegateHelperServiceImp delegateHelper;

    @Autowired
    public CheckDuplicateLeadsDelegate(DelegateHelperServiceImp delegateHelper) {
        this.delegateHelper = delegateHelper;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("Checking for duplicate lead ..");

        boolean hasDuplicate = delegateHelper.checkDuplicate((String) delegateExecution.getVariable("email"));

        delegateExecution.setVariable("isDuplicate", hasDuplicate);

        delegateExecution.setVariable("status", LeadStatus.CheckedDupblicates);

        delegateHelper.updateLeadFromDelegate(delegateExecution);
    }
}
