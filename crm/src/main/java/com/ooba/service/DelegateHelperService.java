package com.ooba.service;

import com.ooba.model.Lead;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import java.util.Map;

/**
 * Created by cleophas on 2018/10/29.
 */
public interface DelegateHelperService {
    void updateLeadFromDelegate(DelegateExecution delegateExecution);

    boolean getIsValid(Long id);

    boolean checkDuplicate(String email);
}
