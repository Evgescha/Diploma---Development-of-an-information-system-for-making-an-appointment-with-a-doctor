package com.hescha.medicalappointment.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Role extends AbstractEntity{
    private String name;
}
