package com.tp4Poo.services;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Invite;
import com.tp4Poo.entities.User;
import com.tp4Poo.repositories.InviteRepository;
import com.tp4Poo.validation.InviteValidator;
@Service
public class InviteService {

    @Autowired
    private InviteRepository inviteR;
    
    @Autowired
    private InviteValidator inviteV;
    @Autowired
    UserLoggedInService uLoggedInS;
    
    
    
    public List<Invite> retrieveAllInvites() {
        return inviteR.findAll();
    }

    
    public Invite addInvite(Invite invite) throws Exception {
    	if (invite.getUser().equals(uLoggedInS.getCurrentUser())) {
    		throw new Exception("No se puede invitar autoinvitar a un evento propio.");
    	}
    	inviteV.validate(invite);
        return inviteR.save(invite);
    }

    
    public Invite findInvite(Long id) {
        return inviteR.findById(id).get();
    }

    
    public Invite replaceInvite(Long id, Invite invite) {
        Invite i=inviteR.findById(id).get();
        i.setEvent(invite.getEvent());
        i.setUser(invite.getUser());
        return inviteR.save(i);
    }

    
    public void deleteInvite(Long id) {
        inviteR.deleteById(id);
    
    }

    
    public Invite findInviteByUserAndEvent(User user, Event event) {
        return inviteR.findByUserAndEvent(user, event);   
    }
    
    
    public List<Invite> findInviteByEvent(Event event) {
        return inviteR.findByEvent(event);   
    }

    
    public List<Invite> findInviteByUser(User user) {
        return inviteR.findByUser(user);
    }

    
    
    
}
