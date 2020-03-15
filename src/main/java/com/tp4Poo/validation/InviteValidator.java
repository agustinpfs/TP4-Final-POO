package com.tp4Poo.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tp4Poo.entities.Invite;
import com.tp4Poo.services.InviteService;

@Component(value = "SimpleInviteValidator")
public class InviteValidator {

    @Autowired
    InviteService inviteService;
   

    public void validate(Invite invite) throws Exception {
    	
        if (invite.getUser() == null) {
            throw new Exception("La invitacion no posee usuario");
        }
        if (invite.getEvent() == null) {
            throw new Exception("La invitacion no posee evento");
        }
        if (inviteService.findInviteByUserAndEvent(invite.getUser(), invite.getEvent()) != null) {
            throw new Exception("Ya existe la invitación.");
        }
    }

}
