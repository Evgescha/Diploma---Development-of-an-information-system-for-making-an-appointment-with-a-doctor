package com.hescha.medicalappointment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
    @OneToMany
    private List<Appointment> appointments = new ArrayList<>();

    public String fullName(){
        return firstname +" "+ lastname;
    }
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

    public List<Appointment> getActiveAppointments() {
        return appointments.stream().filter(appointment -> appointment.getStatus() == AppointmentStatus.CREATED).collect(Collectors.toList());
    }
}