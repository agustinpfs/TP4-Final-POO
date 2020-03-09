package com.tp4Poo.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.Event;

@Service
public interface EventService {
    
    public List<Event> events();
    
    public List<Event> findEventsByOwnerId(long ownerId);
    
    public Event create(Event event) throws Exception;
    
    public Event find(Long id);
    
    public Event update(Long id,Event event) throws Exception;

    public void delete(Long id) throws Exception;
    
}
