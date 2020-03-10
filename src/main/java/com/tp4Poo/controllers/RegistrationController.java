/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp4Poo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unnoba.eventos.entities.Event;
import com.unnoba.eventos.entities.Invite;
import com.unnoba.eventos.entities.Registration;
import com.unnoba.eventos.entities.User;
import com.unnoba.eventos.services.EventService;
import com.unnoba.eventos.services.InviteService;
import com.unnoba.eventos.services.RegistrationService;
import com.unnoba.eventos.services.UserLoggedInService;

/**
 *
 * @author root
 */
@Controller
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserLoggedInService uLoggedIn;

    @Autowired
    private InviteService inviteService;

    @GetMapping("/myRegistrations")
    public String registrate(Model model) {
    	User u= uLoggedIn.loggedIn();
        List<Registration> registrations = registrationService.findRegistrationByUser(u.getId());
        model.addAttribute("registrations", registrations);
        model.addAttribute("LoggedinUser", u);
        return "registrations/myRegistrations";
    }

    @GetMapping("/{id}/registrate")
    public String registrate(@PathVariable Long id, Model model) {
        Event e = eventService.findEvent(id);
        model.addAttribute("event", e);
        return "registrations/registrate";
    }

    @GetMapping("/{id}/confirmRegistration")

    public String confirmRegistration(@PathVariable Long id, Model model) throws Exception {
        try {
        	User u= uLoggedIn.loggedIn();
            Event e = eventService.findEvent(id);

            if (e.isPrivateEvent()) {
                Invite invite = inviteService.findInviteByUserAndEvent(u, e);

                if (invite == null) {
                    throw new Exception("No invitation");
                }
            }

            if (e.getCost() > 0) {
                return "redirect:/payments/{id}";
            } else {
                registrationService.createRegistration(id);
                return "registrations/confirmedRegistration";
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

}
