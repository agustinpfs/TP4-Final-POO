package com.tp4Poo.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.tp4Poo.entities.Invite;
import com.tp4Poo.entities.Registration;
import com.tp4Poo.services.InviteService;

@Component(value = "inviteValidator")
public class RegistrationInviteValidator extends ValidationDecorator{
    
    @Autowired
    private InviteService inviteService;
    
    public RegistrationInviteValidator(
            @Qualifier("SimpleRegistrationValidator") 
            IRegistrationValidator delegate) {
        super(delegate);
    }

    @Override
    public void validate(Registration registration) throws Exception{
        //valida que existe una invitacion para el usuario a este evento
        if(registration.getEvent().isPrivateEvent()){
            Invite invite = inviteService.findInviteByUserAndEvent(
                    registration.getUser(),registration.getEvent());
            if (invite == null) throw new Exception("Sin invitacion");
        }
        super.validate(registration);
    }
    
}
    