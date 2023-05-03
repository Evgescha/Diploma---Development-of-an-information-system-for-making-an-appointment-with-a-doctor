package com.hescha.medicalappointment.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class Contact extends AbstractEntity {
    private String name;
    private String email;
    private String subject;
    @Column(length = 3500)
    private String message;
    private ContactStatus contactStatus = ContactStatus.NEW;
}
