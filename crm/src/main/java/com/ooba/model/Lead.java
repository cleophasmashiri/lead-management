package com.ooba.model;


import javax.persistence.*;

/**
 * Created by cleophas on 2018/10/20.
 */

@Entity
@Table(name="Leads")
public class Lead {

    public Lead() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lead_id")
    private long id;
    private String firstName;
    private String lastame;
    private String emailAddress;
    private String notes;
    private String address;
    private String status;
    private String company;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastame() {
        return lastame;
    }

    public void setLastame(String lastame) {
        this.lastame = lastame;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddres(String emailAddres) {
        this.emailAddress = emailAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
