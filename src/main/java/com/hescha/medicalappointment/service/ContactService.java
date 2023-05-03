package com.hescha.medicalappointment.service;

import com.hescha.medicalappointment.model.Contact;
import com.hescha.medicalappointment.repository.ContactRepository;
import org.springframework.stereotype.Service;


@Service
public class ContactService extends CrudService<Contact> {


    public ContactService(ContactRepository repository) {
        super(repository);
    }

    public Contact update(Long id, Contact entity) {
        Contact read = read(id);
        if (read == null) {
            throw new RuntimeException("Entity Contact not found");
        }
        updateFields(entity, read);
        return update(read);

    }

    private void updateFields(Contact entity, Contact read) {
        read.setName(entity.getName());
        read.setEmail(entity.getEmail());
        read.setSubject(entity.getSubject());
        read.setMessage(entity.getMessage());
        read.setContactStatus(entity.getContactStatus());
    }
}
