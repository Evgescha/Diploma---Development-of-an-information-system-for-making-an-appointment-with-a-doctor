package com.hescha.medicalappointment.repository;

import com.hescha.medicalappointment.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByOwner(User owner);

    Appointment findByCategory(Category category);

    Appointment findByDate(LocalDate date);

    Appointment findByStatus(AppointmentStatus status);

    Appointment findByTimeSlot(TimeSlot timeSlot);
    Optional<Appointment> findByCategoryAndTimeSlotAndDate(Category category, TimeSlot timeSlot, LocalDate date);

    Set<Appointment> findAllByCategoryAndDate(Category category, LocalDate date);


}
