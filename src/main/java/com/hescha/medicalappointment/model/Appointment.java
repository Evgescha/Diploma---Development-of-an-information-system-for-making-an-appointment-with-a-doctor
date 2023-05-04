package com.hescha.medicalappointment.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
public class Appointment extends AbstractEntity{
    @ManyToOne
    private User owner;
    @ManyToOne
    private Category category;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Column(length = 3000)
    private String message;
    private AppointmentStatus status = AppointmentStatus.CREATED;
    private TimeSlot timeSlot = TimeSlot.H09_M00_TO_H09_M30;
}
