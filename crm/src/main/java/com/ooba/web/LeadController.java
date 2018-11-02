package com.ooba.web;

import com.ooba.model.Lead;
import com.ooba.model.LeadStatus;
import com.ooba.service.LeadService;
import com.ooba.service.LeadWorkflowFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by cleophas on 2018/10/28.
 */

@RestController
@RequestMapping("/api/leads")
@CrossOrigin
public class LeadController {

    private LeadService leadService;

    private LeadWorkflowFacadeService leadWorkflowFacadeService;

    @Autowired
    public LeadController(LeadService leadService, LeadWorkflowFacadeService leadWorkflowFacadeService) {
        this.leadService = leadService;
        this.leadWorkflowFacadeService = leadWorkflowFacadeService;
    }

    @GetMapping
    public ResponseEntity<List<Lead>> getAllLeads() {
        return new ResponseEntity<>(leadService.getLeads(), HttpStatus.OK);
    }

    @GetMapping("/status/{statusValue}")
    public ResponseEntity<List<Lead>> getLeadsByStatus(@PathVariable String statusValue) {
        return new ResponseEntity<>(leadService.getLeadsByStatus(EnumUtils.getEnumIgnoreCase(LeadStatus.class, statusValue)), HttpStatus.OK);
    }

    @GetMapping("/email/{emailAddress}")
    public ResponseEntity<List<Lead>> getLeadsByEmail(@PathVariable String emailAddress) {
        return new ResponseEntity<>(leadService.getLeadsByEmailAddress(emailAddress), HttpStatus.OK);
    }

    @PostMapping
    public void createNew(@RequestBody Lead lead) {
        leadWorkflowFacadeService.createNewLead(lead);
    }

    @PostMapping("/allocate/{taskId}/{assignedToId}")
    public void allocate(@PathVariable String taskId, @PathVariable String assignedToId) {
        leadWorkflowFacadeService.allocate(taskId, assignedToId);
    }

    @PostMapping("/capture/{taskId}")
    public void capture(@PathVariable String taskId, @RequestBody Lead lead) {
        leadWorkflowFacadeService.capture(taskId, lead);
    }

    @PostMapping("/contact/{taskId}")
    public void contact(@PathVariable String taskId, @RequestBody Lead lead) {
        leadWorkflowFacadeService.contact(taskId, lead);
    }

    @PostMapping("/provideInfo/{taskId}")
    public void provideInfo(@PathVariable String taskId, @RequestBody Lead lead) {
        leadWorkflowFacadeService.provideInfo(taskId,lead);
    }
}
