package com.hescha.medicalappointment.service;

import com.hescha.medicalappointment.model.Role;
import com.hescha.medicalappointment.model.User;
import com.hescha.medicalappointment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class UserService extends CrudService<User> implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь " + username + " не был найден в базе");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), List.of());
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User findByUsernameIgnoreCase(String username) {
        return repository.findByUsernameIgnoreCase(username);
    }

    public List<User> findByUsernameContains(String username) {
        return repository.findByUsernameContains(username);
    }

    public List<User> findByPassword(String password) {
        return repository.findByPassword(password);
    }

    public List<User> findByPasswordContains(String password) {
        return repository.findByPasswordContains(password);
    }

    public List<User> findByFirstname(String firstname) {
        return repository.findByFirstname(firstname);
    }

    public List<User> findByFirstnameContains(String firstname) {
        return repository.findByFirstnameContains(firstname);
    }

    public List<User> findByLastname(String lastname) {
        return repository.findByLastname(lastname);
    }

    public List<User> findByLastnameContains(String lastname) {
        return repository.findByLastnameContains(lastname);
    }

    public List<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<User> findByEmailContains(String email) {
        return repository.findByEmailContains(email);
    }

    public List<User> findByImage(String image) {
        return repository.findByImage(image);
    }

    public List<User> findByImageContains(String image) {
        return repository.findByImageContains(image);
    }

    public List<User> findByAddress(String address) {
        return repository.findByAddress(address);
    }

    public List<User> findByAddressContains(String address) {
        return repository.findByAddressContains(address);
    }

    public User findByBirthDate(LocalDate birthDate) {
        return repository.findByBirthDate(birthDate);
    }

    public Set<User> findByRolesContains(com.hescha.medicalappointment.model.Role roles) {
        return repository.findByRolesContains(roles);
    }

    public List<User> findByAppointmentsContains(com.hescha.medicalappointment.model.Appointment appointments) {
        return repository.findByAppointmentsContains(appointments);
    }


    public User update(Long id, User entity) {
        User read = read(id);
        if (read == null) {
            throw new RuntimeException("Entity User not found");
        }
        updateFields(entity, read);
        return update(read);

    }

    private void updateFields(User entity, User read) {
        read.setUsername(entity.getUsername());
        read.setPassword(passwordEncoder.encode(entity.getPassword()));
        read.setFirstname(entity.getFirstname());
        read.setLastname(entity.getLastname());
        read.setEmail(entity.getEmail());
        read.setImage(entity.getImage());
        read.setAddress(entity.getAddress());
        read.setBirthDate(entity.getBirthDate());
    }

    public boolean registerNew(User entity) {
        Role read = roleService.read(1);
        entity.getRoles().add(read);
        if (repository.findByUsername(entity.getUsername()) != null) {
            return false;
        }
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        try {
            create(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
