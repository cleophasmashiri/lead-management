package com.ooba.delegates;

import com.ooba.model.Lead;
import com.ooba.model.LeadStatus;
import com.ooba.service.DelegateHelperService;
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

    private DelegateHelperService delegateHelper;

    @Autowired
    public UpdateAndValidateLeadDelegate(DelegateHelperService delegateHelper) {
        this.delegateHelper = delegateHelper;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("UpdateAndValidateLeadDelegate .." + (Long) delegateExecution.getVariable("id"));

        boolean isValid =  delegateHelper.getIsValid((Long) delegateExecution.getVariable("id"));

        if(isValid) {
            delegateExecution.setVariable("status", LeadStatus.Captured);
        }

        delegateExecution.setVariable("isValid", isValid);

        delegateHelper.updateLeadFromDelegate(delegateExecution);

    }

}
