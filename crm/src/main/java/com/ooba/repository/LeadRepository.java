package com.ooba.repository;

import com.ooba.model.Lead;
import com.ooba.model.LeadStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by cleophas on 2018/10/20.
 */
@Repository
public interface LeadRepository extends CrudRepository<Lead, Long> {
    List<Lead> findAll();
    List<Lead> findByEmailAddress(String emailAddress);
    List<Lead> findByStatus(LeadStatus statusValue);
}
