package com.tp4Poo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp4Poo.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

    public List<Event> findEventsByOwnerId(long ownerId);
    
    public List<Event> findAllEvents();
       
}
