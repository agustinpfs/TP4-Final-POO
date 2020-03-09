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
import com.tp4Poo.entities.Payment;
import com.tp4Poo.entities.Registration;
import com.tp4Poo.entities.User;
import com.tp4Poo.services.EventService;
import com.tp4Poo.services.InviteService;
import com.tp4Poo.services.PaymentService;
import com.tp4Poo.services.RegistrationService;
import com.tp4Poo.services.SessionService;
import com.tp4Poo.services.UserService;

@Controller
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private InviteService inviteService;

    @GetMapping("/myRegistrations")
    public String registrate(Model model) {
        Long id = sessionService.getCurrentUser().getId();
        List<Registration> registrations = registrationService.findByUser(id);
        model.addAttribute("registrations", registrations);
        model.addAttribute("currentUser", sessionService.getCurrentUser());
        return "registrations/myRegistrations";
    }

    @GetMapping("/{id}/registrate")
    public String registrate(@PathVariable Long id, Model model) {
        Event e = eventService.find(id);
        model.addAttribute("event", e);
        return "registrations/registrate";
    }

    @GetMapping("/{id}/confirmRegistration")

    public String confirmRegistration(@PathVariable Long id, Model model) throws Exception {
        try {

            Event e = eventService.find(id);

            if (e.isPrivateEvent()) {
                Invite invite = inviteService.findByUserAndEvent(sessionService.getCurrentUser(), e);

                if (invite == null) {
                    throw new Exception("Sin invitacion");
                }
            }

            if (e.getCost() > 0) {
                return "redirect:/payments/{id}";
            } else {
                registrationService.create(id);
                return "registrations/confirmedRegistration";
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

}
