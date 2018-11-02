package com.ooba.delegates;

import com.ooba.model.Lead;
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
public class CreateLeadDelegate implements JavaDelegate {

    private static Logger LOGGER = LoggerFactory.getLogger(CreateLeadDelegate.class);

    private DelegateHelperServiceImp delegateHelper;

    @Autowired
    public CreateLeadDelegate(DelegateHelperServiceImp delegateHelper) {
        this.delegateHelper = delegateHelper;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("Creating a new lead ..");

        delegateExecution.setVariable("status", LeadStatus.New);

        delegateHelper.updateLeadFromDelegate(delegateExecution);


    }
}
