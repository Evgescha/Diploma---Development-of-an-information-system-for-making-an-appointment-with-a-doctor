package com.hescha.medicalappointment.repository;

import com.hescha.medicalappointment.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);

    List<Category> findByNameContains(String name);

    List<Category> findByAppointmentsContains(com.hescha.medicalappointment.model.Appointment appointments);
}
