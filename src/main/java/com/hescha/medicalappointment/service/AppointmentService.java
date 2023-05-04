package com.hescha.medicalappointment.service;

import com.hescha.medicalappointment.model.*;
import com.hescha.medicalappointment.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AppointmentService extends CrudService<Appointment> {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        super(repository);
        this.repository = repository;
    }


    public Optional<Appointment> findByCategoryAndTimeSlotAndDate(Category category, TimeSlot timeSlot, LocalDate date) {
        return repository.findByCategoryAndTimeSlotAndDate(category, timeSlot, date);
    }

    public Appointment findByOwner(User owner) {
        return repository.findByOwner(owner);
    }

    public Appointment findByCategory(Category category) {
        return repository.findByCategory(category);
    }

    public Appointment findByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public Appointment findByStatus(AppointmentStatus status) {
        return repository.findByStatus(status);
    }

    public Appointment findByTimeSlot(TimeSlot timeSlot) {
        return repository.findByTimeSlot(timeSlot);
    }


    public Appointment update(Long id, Appointment entity) {
        Appointment read = read(id);
        if (read == null) {
            throw new RuntimeException("Entity Appointment not found");
        }
        updateFields(entity, read);
        return update(read);

    }

    private void updateFields(Appointment entity, Appointment read) {
        read.setOwner(entity.getOwner());
        read.setCategory(entity.getCategory());
        read.setDate(entity.getDate());
        read.setStatus(entity.getStatus());
        read.setTimeSlot(entity.getTimeSlot());
    }

    public Set<Appointment> findAllByCategoryAndDate(Category category, LocalDate date) {
        return repository.findAllByCategoryAndDate(category, date);
    }
}
