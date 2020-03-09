package com.tp4Poo.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Invite;
import com.tp4Poo.entities.User;
import com.tp4Poo.repositories.InviteRepository;

/**
 *
 * @author root
 */
@Service
public class InviteServiceImp implements InviteService {

    @Autowired
    private InviteRepository inviteRepository; //instancia de InviteRepository
    
//    @Autowired
//    private InviteValidator inviteValidator;
    
    
    @Override
    public List<Invite> invites() {
        return inviteRepository.findAll();
    }

    @Override
    public Invite create(Invite invite) throws Exception {
//        inviteValidator.validate(invite);
        return inviteRepository.save(invite);
    }

    @Override
    public Invite find(Long id) {
        return inviteRepository.findById(id).get();
    }

    @Override
    public Invite update(Long id, Invite invite) {
        Invite i=inviteRepository.findById(id).get();
        i.setEvent(invite.getEvent());
        i.setUser(invite.getUser());
        return inviteRepository.save(i);
    }

    @Override
    public void delete(Long id) {
        inviteRepository.deleteById(id);
    
    }

    @Override
    public Invite findByUserAndEvent(User user, Event event) {
        return inviteRepository.findByUserAndEvent(user, event);   
    }
    
    @Override
    public List<Invite> findByEvent(Event event) {
        return inviteRepository.findByEvent(event);   
    }

    @Override
    public List<Invite> findByUser(User user) {
        return inviteRepository.findByUser(user);
    }

    @Override
    public void deleteInvitesByEventId(Long id) {
        System.out.println("quiero borrar");
        //inviteRepository.deleteInvitesByEventId(id);
    }
    
}
