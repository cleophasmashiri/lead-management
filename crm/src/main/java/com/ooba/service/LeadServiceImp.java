package com.ooba.service;

import com.ooba.model.Lead;
import com.ooba.model.LeadStatus;
import com.ooba.repository.LeadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by cleophas on 2018/10/20.
 */

@Service
public class LeadServiceImp implements LeadService {

    private static Logger LOGGER = LoggerFactory.getLogger(LeadServiceImp.class);

    private LeadRepository leadRepository;

    @Autowired
    public LeadServiceImp(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Override
    public boolean checkDuplicate(String email) {

        LOGGER.info("checkDuplicate ..");

        Collection<Lead> leads = leadRepository.findByEmailAddress(email);

        LOGGER.info("checkDuplicate .. leads: " + leads);
        LOGGER.info("checkDuplicate .. leads count: " + leads.size());

        if (leads == null || leads.size() <= 1)
            return false;
        else
            return true;
    }

    @Override
    public void createNewLead(Lead lead) {
        leadRepository.save(lead);
    }

    @Override
    public Lead getLead(Long id) {
        return leadRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Lead not found"));
    }

    @Override
    public boolean getIsValid(Lead lead) {
        if("".equals(lead.getFirstName()) || "".equals(lead.getLastName()) || "".equals(lead.getEmailAddress()) || "".equals(lead.getNotes())) {
            return false;
        }
        return true;
    }

    @Override
    public void update(Lead lead) {
        leadRepository.save(lead);
    }

    @Override
    public void delete(Long id) {
        leadRepository.deleteById(id);
    }

    @Override
    public List<Lead> getLeads() {
        return leadRepository.findAll();
    }

    @Override
    public List<Lead> getLeadsByStatus(LeadStatus statusValue) {
        return leadRepository.findByStatus(statusValue);
    }

    @Override
    public List<Lead> getLeadsByEmailAddress(String emailAddress) {
        return leadRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public boolean getIsValid(Long id) {
        Lead lead = getLead(id);
        return getIsValid(lead);
    }
}
