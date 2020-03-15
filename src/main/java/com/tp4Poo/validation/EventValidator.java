package com.tp4Poo.validation;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.tp4Poo.entities.Event;

@Component(value = "SimpleEventValidator")
public class EventValidator {

    public void validate(Event event) throws Exception {
        if (event.getEventName().isEmpty()) {
            throw new Exception("El evento no posee nombre");
        }
        if (event.getOwner() == null) {
            throw new Exception("El evento no posee dueño");
        }
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date today = c.getTime();
        
        if (event.getEventDate() == null || event.getEventDate().before(today)) {
            throw new Exception("La fecha del evento debe ser igual o posterior al dia de hoy.");
        }
        if (event.getStartRegistrations() == null || event.getStartRegistrations().before(today) || event.getStartRegistrations().after(event.getEventDate())) { // Controlo que la fecha de inicio de inscripciones no sea nula, sea mayor o igaul a la fecha actual y menor o igual a la feche del evento.
            throw new Exception("La fecha de comienzo de inscripciones debe estar entre hoy y el dia del evento.");
        }
        if (event.getEndRegistrations() == null || event.getEndRegistrations().before(event.getStartRegistrations()) || event.getEndRegistrations().after(event.getEventDate())) { // Controlo que la fecha de fin de inscripciones no sea nula, sea mayor o igaul que la de comienzo de incripciones y que sea menor o igaul que la fecha del evento.
            throw new Exception("La fecha de fin de inscripciones debe estar entre el dia de comienzo de inscripciones y el dia del evento.");
        }
        if (event.getLugar().isEmpty()) {
            throw new Exception("El evento no posee lugar.");
        }
        if (event.getCapacity() < 1) {
            throw new Exception("La capacidad debe ser 1 o mas.");
        }
        if (event.getCost() < 0) {
            throw new Exception("El costo del evento no puede ser negativo.");
        }

    }

}
