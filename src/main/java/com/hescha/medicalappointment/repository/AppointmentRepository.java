package com.hescha.medicalappointment.repository;

import com.hescha.medicalappointment.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByOwner(User owner);

    Appointment findByCategory(Category category);

    Appointment findByDate(LocalDate date);

    Appointment findByStatus(AppointmentStatus status);

    Appointment findByTimeSlot(TimeSlot timeSlot);
}
