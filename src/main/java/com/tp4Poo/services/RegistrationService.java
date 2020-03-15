package com.tp4Poo.services;


import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Registration;
import com.tp4Poo.entities.User;
import com.tp4Poo.repositories.EventRepository;
import com.tp4Poo.repositories.RegistrationRepository;
import com.tp4Poo.validation.RegistrationValidator;
import com.tp4Poo.validation.ValidationDecorator;


@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationR;

    @Autowired
    private EventRepository eventR;

    @Autowired
    private RegistrationValidator validator;
    
    @Autowired
    private ValidationDecorator validatorD;

    @Autowired
    private UserLoggedInService uLoggedInS;

    public List<Registration> retrieveAllRegistrations() {
        return registrationR.findAll();

    }

    @Transactional
    public void addRegistration(Long eventId) throws Exception {
        User user = uLoggedInS.getCurrentUser();
        Event e = eventR.getOne(eventId);
        Registration reg = new Registration();
        reg.setEvent(e);
        reg.setUser(user);
        Date today = new Date();
        reg.setCreatedAt(today);
        validator.validate(reg);
        validatorD.validate(reg);
        registrationR.save(reg);
    }

   
    public Registration findRegistration(Long id) {
        return registrationR.findById(id).get();
    }

    
    public void deleteRegistration(Long id) {
        registrationR.deleteById(id);
    }

   
    public Registration findRegistrationByEventAndUser(Event event, User user) {
        List<Registration> registrations = registrationR.findByEventAndUser(event, user);
        if (registrations.isEmpty()) {
            return null;
        } else {
            return registrations.get(0);
        }
    }
    
   
    public List<Registration> findRegistrationByEvent(Event event) {
        List<Registration> registrations = registrationR.findByEvent(event);
        return registrations;
    }

    public List<Registration> findRegistrationByUser(Long id) {
        List<Registration> registrations = registrationR.findByUserId(id);
        return registrations;
    }

}
