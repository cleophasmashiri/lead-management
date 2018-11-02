package com.ooba.service;

import com.ooba.model.Lead;

/**
 * Created by cleophas on 2018/10/28.
 */
public interface LeadWorkflowFacadeService {

    void createNewLead(Lead lead);

    void allocate(String taskId, String assignedToId);

    void capture(String taskId, Lead lead);

    void contact(String taskId, Lead lead);

    void provideInfo(String taskId, Lead lead);
}
