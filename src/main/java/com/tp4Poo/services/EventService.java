package com.tp4Poo.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.Event;
import com.tp4Poo.repositories.EventRepository;
import com.tp4Poo.validation.EventValidator;


@Service
public class EventService  {

    @Autowired
    private UserLoggedInService uLoggedInS;

    @Autowired
    private EventRepository eventR;
    
    @Autowired
	private EventValidator eventV;

    public List<Event> retrieveAllEvents() {
        return eventR.findAllEvents();
    }

    public Event addEvent(Event event) throws Exception {
        event.setOwner(uLoggedInS.getCurrentUser());
        eventV.validate(event);
        return eventR.save(event);
    }

    public Event findEvent(Long id) {
        return eventR.findById(id).get();
    }

    public Event replaceEvent(Long id, Event event) throws Exception {
        Event temp = this.findEvent(id);
        if (event.getCapacity() >= temp.cantidadRegistrations()) {     
            if (temp.hasRegistrations() && temp.getCost() != event.getCost()) {  
                throw new Exception("No se puede cambiar el costo si ya hay inscriptos");
            } else {
                temp.setEventName(event.getEventName()); 
                temp.setCapacity(event.getCapacity());
                temp.setCost(event.getCost());
                temp.setStartRegistrations(event.getStartRegistrations());
                temp.setEndRegistrations(event.getEndRegistrations());
                temp.setEventDate(event.getEventDate());
                temp.setPrivateEvent(event.isPrivateEvent());
                temp.setLugar(event.getLugar());
                eventV.validate(temp);
                return eventR.save(temp);
            }
        } else {
            throw new Exception("La capacidad no puede ser menor que la cantidad de inscriptos");
        }
    }

    
    public void delete(Long id) throws Exception {
        Event event = this.findEvent(id);
        if (!event.hasRegistrations()) {
            eventR.deleteById(id);
        } else {
            throw new Exception("No se puede eliminar el evento porque posee inscripciones");
        }
    }

    public List<Event> findEventsByOwnerId(long ownerId) {
        return eventR.findEventsByOwnerId(ownerId);
    }

}
