/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp4Poo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unnoba.eventos.entities.Event;
import com.unnoba.eventos.entities.Invite;
import com.unnoba.eventos.entities.Payment;
import com.unnoba.eventos.entities.Registration;
import com.unnoba.eventos.entities.User;
import com.unnoba.eventos.services.*;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserLoggedInService uLoggedIn;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private InviteService inviteService;
    

    @GetMapping
    public String events(Model model) {
        List<Event> events = eventService.retrieveAllEvents();
        model.addAttribute("events", events);
        model.addAttribute("currentUser", uLoggedIn.loggedIn());
        return "events/events";
    }

    @GetMapping("/myEvents")
    public String myEvents(Model model) {
    	User u= uLoggedIn.loggedIn();
    	List<Event> events = eventService.findEventById(u.getId());
        model.addAttribute("events", events);
        model.addAttribute("currentUser", u);
        return "events/myEvents";
    }

    @GetMapping("/new")
    public String eventNew(Model model) {
        model.addAttribute("event", new Event());
        return "events/new";
    }

    @PostMapping
    public String createEvent(@ModelAttribute Event event, Model model) throws Exception {
        try {
            eventService.createEvent(event);
            return "redirect:/events/myEvents";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }

    }

    @GetMapping("/{id}/delete")
    public String deleteEvent(Model model, @PathVariable("id") Long id) throws Exception {
        try {
        	User u= uLoggedIn.loggedIn();
            Event event = eventService.findEvent(id);
            if (event.getOwner().getId().equals(u.getId())) { 
                if (inviteService.findInviteByEvent(event).isEmpty()) {
                    eventService.deleteEvent(id);
                    return "redirect:/events/myEvents";
                }
                throw new Exception("Cannot be deleted because there are invitations");
            }

            throw new Exception("Invalid User");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/{id}/edit")
    public String editEvent(@PathVariable Long id, Model model) throws Exception {
        try {
        	User u= uLoggedIn.loggedIn();
            Event event = eventService.findEvent(id);
            if (event.getOwner().getId().equals(u.getId())) {
                model.addAttribute("event", event);
                return "events/edit";
            }
            throw new Exception("Invalid User "+ u);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @PostMapping("/{id}/update")
    public String updateEvent(Model model, @PathVariable Long id, @ModelAttribute Event event) throws Exception {
        try {
        	User u= uLoggedIn.loggedIn();
            Event temp = eventService.findEvent(id);
            if (temp.getOwner().getId().equals(u.getId())) { 
                eventService.updateEvent(id, event);
                return "redirect:/events/myEvents";
            }

            throw new Exception("Invalid User");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/{id}/eventDetails")
    public String eventDetails(@PathVariable Long id, Model model) throws Exception {
        try {
        	User u= uLoggedIn.loggedIn();
            Event event = eventService.findEvent(id);
            if (event.getOwner().getId().equals(u.getId())) {   
                List<Invite> invites = inviteService.findInviteByEvent(event);
                model.addAttribute("event", event);
                model.addAttribute("invites", invites);
                model.addAttribute("currentUser", u);

                if (event.getCost() > 0) {
                    List<Payment> payments = paymentService.findPaymentByEvent(event);
                    model.addAttribute("payments", payments);
                    return "events/eventPay";
                }
                List<Registration> registrations = event.getRegistrations();
                model.addAttribute("registrations", registrations);
                return "events/eventFree";
            }
            throw new Exception("Invalid User");

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }
        @InitBinder
        public void initBinder
        (final WebDataBinder binder
        
            ) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

    }
