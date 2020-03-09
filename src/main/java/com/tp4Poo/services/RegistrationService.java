package com.tp4Poo.services;


import java.util.List;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Registration;
import com.tp4Poo.entities.User;


@Service
public interface RegistrationService {

    public List<Registration> registrations();

    public void create(Long eventId) throws Exception;

    public Registration find(Long id);

    public void delete(Long id);

    public Registration findByEventAndUser(Event event, User user);
    
    public List<Registration> findByEvent(Event event);

    public List<Registration> findByUser(Long id);
}
