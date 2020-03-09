package com.tp4Poo.services;


import java.util.List;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Invite;
import com.tp4Poo.entities.User;


@Service
public interface InviteService {

    public List<Invite> invites();

    public Invite create(Invite invite) throws Exception;

    public Invite find(Long id);

    public Invite update(Long id, Invite invite);

    public void delete(Long id);

    public Invite findByUserAndEvent(User user, Event event);
    
    public List<Invite> findByEvent(Event event);

    public List<Invite> findByUser(User user);

    public void deleteInvitesByEventId(Long id);
}
