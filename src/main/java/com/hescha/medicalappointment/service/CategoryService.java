package com.hescha.medicalappointment.service;

import com.hescha.medicalappointment.model.Category;
import com.hescha.medicalappointment.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService extends CrudService<Category> {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Category> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Category> findByNameContains(String name) {
        return repository.findByNameContains(name);
    }

    public List<Category> findByAppointmentsContains(com.hescha.medicalappointment.model.Appointment appointments) {
        return repository.findByAppointmentsContains(appointments);
    }


    public Category update(Long id, Category entity) {
        Category read = read(id);
        if (read == null) {
            throw new RuntimeException("Entity Category not found");
        }
        updateFields(entity, read);
        return update(read);

    }

    private void updateFields(Category entity, Category read) {
        read.setName(entity.getName());
    }
}
