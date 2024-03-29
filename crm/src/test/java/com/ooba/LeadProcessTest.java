package com.ooba;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.camunda.bpm.engine.test.assertions.cmmn.CmmnAwareTests.complete;

/**
 * Created by cleophas on 2018/10/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Deployment(resources = "leadProcess.bpmn")
public class LeadProcessTest {

    @Test
    public void successPath() {

        Map<String, Object> variables = new HashMap<>();
        ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey("leadProcess", variables);

        assertThat(processInstance)
                .isStarted()
                .isWaitingAt("captureAndLead")
                .task()
                .hasCandidateGroup("FirstLineAgents");

        Map<String, Object> captureAndAllocateLeadVariables = new HashMap<>();
        captureAndAllocateLeadVariables.put("firstName", "Cleophas");
        captureAndAllocateLeadVariables.put("lastName", "Mashiri");
        captureAndAllocateLeadVariables.put("emailAddress", "cleophas@gmail.com");
        captureAndAllocateLeadVariables.put("notes", "notes");

        complete(task(), captureAndAllocateLeadVariables);

        assertThat(processInstance)
                .isWaitingAt("allocateLead")
                .task()
                .hasCandidateGroup("FirstLineAgents");
        Map<String, Object> allocateLeadVariables = new HashMap<>();
        complete(task(), allocateLeadVariables);

        assertThat(processInstance)
                .isWaitingAt("captureLeadDetails")
                .task()
                .hasCandidateGroup("SecondLineAgents");

        Map<String, Object> captureLeadDetailsVariables = new HashMap<>();
        complete(task(), captureLeadDetailsVariables);

        assertThat(processInstance)
                .isWaitingAt("contactLead")
                .task()
        .hasCandidateGroup("SecondLineAgents");

        Map<String, Object> contactLeadVariables = new HashMap<>();
        complete(task(), contactLeadVariables);

        assertThat(processInstance)
                .isEnded();

    }
}
