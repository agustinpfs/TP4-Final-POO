package com.tp4Poo.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Invite;
import com.tp4Poo.entities.Registration;
import com.tp4Poo.services.EventService;
import com.tp4Poo.services.InviteService;
import com.tp4Poo.services.RegistrationService;
import com.tp4Poo.services.UserLoggedInService;

@Controller
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationS;

    @Autowired
    private EventService eventS;

    @Autowired
    private UserLoggedInService uLoggedInS;

    @Autowired
    private InviteService inviteService;

    @GetMapping("/myRegistrations")
    public String registrate(Model model) {
        Long id = uLoggedInS.getCurrentUser().getId();
        List<Registration> registrations = registrationS.findRegistrationByUser(id);
        model.addAttribute("registrations", registrations);
        model.addAttribute("currentUser", uLoggedInS.getCurrentUser());
        return "registrations/myRegistrations";
    }

    @GetMapping("/registrate/{id}")
    public String registrate(@PathVariable Long id, Model model) {
        Event e = eventS.findEvent(id);
        model.addAttribute("event", e);
        return "registrations/registrate";
    }

    @GetMapping("/confirmRegistration/{id}")

    public String confirmRegistration(@PathVariable Long id, Model model) throws Exception {
        try {

            Event event = eventS.findEvent(id);

            if (event.isPrivateEvent()) {
                Invite invite = inviteService.findInviteByUserAndEvent(uLoggedInS.getCurrentUser(), event);

                if (invite == null) {
                    throw new Exception("Debe tener invitación para este registro");
                }
            }

            if (event.getCost() > 0) {
                return "redirect:/payments/{id}";
            } else {
                registrationS.addRegistration(id);
                return "registrations/confirmedRegistration";
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

}
