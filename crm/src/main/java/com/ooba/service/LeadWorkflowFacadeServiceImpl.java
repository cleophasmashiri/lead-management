package com.ooba.service;

import com.ooba.model.Lead;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cleophas on 2018/10/28.
 */

@Service
public class LeadWorkflowFacadeServiceImpl implements LeadWorkflowFacadeService {

    private final Logger logger = LoggerFactory.getLogger(LeadWorkflowFacadeService.class);

    public static final String PROCESS_DEFINITION_KEY = "leadProcess";

    private ProcessEngine engine;

    private TaskService taskService;

    @Autowired
    public LeadWorkflowFacadeServiceImpl(TaskService taskService, ProcessEngine engine) {
        this.taskService = taskService;
        this.engine = engine;
    }

    @Override
    public void createNewLead(Lead lead) {
        Map<String, Object> variables = getLeadVariables(lead);
        ProcessInstance processInstance = engine.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);

    }

    @Override
    public void allocate(String taskId, String assignedToId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignedToId", assignedToId);
        taskService.complete(taskId, variables);
    }

    @Override
    public void capture(String taskId, Lead lead) {
        Map<String, Object> variables = getLeadVariables(lead);
        taskService.complete(taskId, variables);
    }

    @Override
    public void contact(String taskId, Lead lead) {
        Map<String, Object> variables = getLeadVariables(lead);
        taskService.complete(taskId, variables);
    }

    @Override
    public void provideInfo(String taskId, Lead lead) {
        Map<String, Object> variables = getLeadVariables(lead);
        taskService.complete(taskId, variables);
    }

    private Map<String, Object> getLeadVariables(Lead lead) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("firstName", lead.getFirstName());
        variables.put("lastName", lead.getLastName());
        variables.put("address", lead.getAddress());
        variables.put("company", lead.getCompany());
        variables.put("notes", lead.getNotes());
        variables.put("status", lead.getStatus());
        variables.put("leadId", lead.getId());
        variables.put("emailAddress", lead.getEmailAddress());

        return variables;
    }
}
