package com.tp4Poo.repositories;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Invite;
import com.tp4Poo.entities.User;


@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

    public Invite findByUserAndEvent(User user, Event event);

    public List<Invite> findByUser(User user);

    public List<Invite> findByEvent(Event event);
    
}

