package com.ooba.service;

import com.ooba.model.Lead;
import com.ooba.repository.LeadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by cleophas on 2018/10/20.
 */

@Component
public class LeadServiceImp implements LeadService {

    private static Logger LOGGER = LoggerFactory.getLogger(LeadServiceImp.class);

    @Autowired
    private LeadRepository leadRepository;

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
        if("".equals(lead.getFirstName()) || "".equals(lead.getLastame()) || "".equals(lead.getEmailAddress()) || "".equals(lead.getNotes())) {
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
}
