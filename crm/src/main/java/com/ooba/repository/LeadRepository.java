package com.ooba.repository;

import com.ooba.model.Lead;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by cleophas on 2018/10/20.
 */
@Repository
public interface LeadRepository extends CrudRepository<Lead, Long> {
    Collection<Lead> findAll();
    Collection<Lead> findByEmailAddress(String emailAddress);
}
