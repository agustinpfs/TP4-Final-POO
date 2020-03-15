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

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Invite;
import com.tp4Poo.entities.Payment;
import com.tp4Poo.entities.Registration;
import com.tp4Poo.services.EventService;
import com.tp4Poo.services.InviteService;
import com.tp4Poo.services.PaymentService;
import com.tp4Poo.services.UserService;


@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventS;

    @Autowired
    private UserService uLoggedInS;

    @Autowired
    private PaymentService paymentS;

    @Autowired
    private InviteService inviteS;

    @GetMapping
    public String index(Model model) {
        List<Event> events = eventS.retrieveAllEvents();
        model.addAttribute("events", events);
        model.addAttribute("currentUser", uLoggedInS.currentUser());
        return "events/index";
    }

    @GetMapping("/myEvents")
    public String myEvents(Model model) {
        List<Event> events = eventS.findEventsByOwnerId(uLoggedInS.currentUser().getId());
        model.addAttribute("events", events);
        model.addAttribute("currentUser", uLoggedInS.currentUser());
        return "events/myEvents";
    }

    @GetMapping("/new")
    public String eventNew(Model model) {
        model.addAttribute("event", new Event());
        return "events/addEvent";
    }

    @PostMapping
    public String createEvent(@ModelAttribute Event event, Model model) throws Exception {
        try {
            eventS.addEvent(event);
            return "redirect:/events/myEvents";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }

    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable("id") Long id) throws Exception {
        try {
            Event event = eventS.findEvent(id);
            if (Objects.equals(uLoggedInS.currentUser().getId(), event.getOwner().getId())) {    
                if (inviteS.findInviteByEvent(event).isEmpty()) {
                    eventS.delete(id);
                    return "redirect:/events/myEvents";
                }
                throw new Exception("No se puede Borrar por que el evento posee invitaciones");
            }

            throw new Exception("Permiso denegado usuario invalido");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) throws Exception {
        try {
            Event event = eventS.findEvent(id);
            if (uLoggedInS.currentUser().getId().equals(event.getOwner().getId())) {  
                model.addAttribute("event", event);
                return "events/edit";
            }
            throw new Exception("Usuario invalido");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @PostMapping("/{id}/update")
    public String update(Model model, @PathVariable Long id, @ModelAttribute Event event) throws Exception {
        try {
            Event oldEvent = eventS.findEvent(id);
            if (uLoggedInS.currentUser().getId().equals(oldEvent.getOwner().getId())) {     
                eventS.replaceEvent(id, event);
                return "redirect:/events/myEvents";
            }

            throw new Exception("Inválido");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/{id}/eventDetails")
    public String detail(@PathVariable Long id, Model model) throws Exception {
        try {
            Event event = eventS.findEvent(id);
            if (uLoggedInS.currentUser().getId().equals(event.getOwner().getId())) { 
                List<Invite> invites = inviteS.findInviteByEvent(event);
                model.addAttribute("event", event);
                model.addAttribute("invites", invites);
                model.addAttribute("currentUser", uLoggedInS.currentUser());

                if (event.getCost() > 0) {
                    List<Payment> payments = paymentS.findPaymentsByEvent(event);
                    model.addAttribute("payments", payments);
                    return "events/eventDetailsPaid";
                }
                List<Registration> registrations = event.getRegistrations();
                model.addAttribute("registrations", registrations);
                return "events/eventDetailsFree";
            }
            throw new Exception("Usuario inválido");

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(formatter, true));
    }

    }
