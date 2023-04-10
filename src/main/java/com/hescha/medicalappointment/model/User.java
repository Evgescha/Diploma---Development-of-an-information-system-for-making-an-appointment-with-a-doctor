package com.hescha.medicalappointment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Table(name = "myUser")
@Entity
public class User extends AbstractEntity {
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String image;
    private String address;
    private LocalDate birthDate;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
    @OneToMany
    private List<Appointment> appointments = new ArrayList<>();

    @Override
    public String toString() {
        return username;
    }

    public boolean isManager() {
        return roles.stream().anyMatch(role -> role.getName().contains("MANAGER"));
    }

    public boolean isAdmin() {
        return roles.stream().anyMatch(role -> role.getName().contains("ADMIN"));
    }

    public boolean isManagerOrAdmin() {
        return isAdmin() || isManager();
    }
}