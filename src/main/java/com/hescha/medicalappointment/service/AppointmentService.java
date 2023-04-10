package com.hescha.medicalappointment.service;

import com.hescha.medicalappointment.model.*;
import com.hescha.medicalappointment.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AppointmentService extends CrudService<Appointment> {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        super(repository);
        this.repository = repository;
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
}
