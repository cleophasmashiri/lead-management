package com.ooba.service;

import com.ooba.model.Lead;

/**
 * Created by cleophas on 2018/10/20.
 */
public interface LeadService {


    boolean checkDuplicate(String email);

    void createNewLead(Lead lead);

    Lead getLead(Long id);

    boolean getIsValid(Lead lead);

    void update(Lead lead);

    void delete(Long id);
}
