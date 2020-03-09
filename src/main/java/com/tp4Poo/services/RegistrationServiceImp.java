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


@Service
public class RegistrationServiceImp implements RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;

//    @Autowired
//    private RegistrationValidator validator;
    
//    @Autowired
//    private ValidationDecorator validatorDecorator;

    @Autowired
    private SessionService sessionService;

    @Override
    public List<Registration> registrations() {
        return registrationRepository.findAll();

    }

    @Transactional
    @Override
    public void create(Long eventId) throws Exception {
        User user = sessionService.getCurrentUser();
        Event e = eventRepository.getOne(eventId);
        Registration reg = new Registration();
        reg.setEvent(e);
        reg.setUser(user);
        Date today = new Date();
        reg.setCreatedAt(today);
//        validator.validate(reg);
//        validatorDecorator.validate(reg);
        registrationRepository.save(reg);
    }

    @Override
    public Registration find(Long id) {
        return registrationRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        registrationRepository.deleteById(id);
    }

    @Override
    public Registration findByEventAndUser(Event event, User user) {
        List<Registration> registrations = registrationRepository.findByEventAndUser(event, user);
        if (registrations.isEmpty()) {
            return null;
        } else {
            return registrations.get(0);
        }
    }
    
    @Override
    public List<Registration> findByEvent(Event event) {
        List<Registration> registrations = registrationRepository.findByEvent(event);
        return registrations;
    }

    @Override
    public List<Registration> findByUser(Long id) {
        List<Registration> registrations = registrationRepository.findByUserId(id);
        return registrations;
    }

}
