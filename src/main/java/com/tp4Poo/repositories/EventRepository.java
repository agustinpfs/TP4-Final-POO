package com.tp4Poo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tp4Poo.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

    @Query("select e from Event e where e.owner.id = :id")
	public List<Event> findEventsByOwnerId(@Param("id") Long id); 
    @Query("select e from Event e order by e.eventDate ASC")
	public List<Event> findAllEvents(); 
}
