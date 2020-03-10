/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp4Poo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unnoba.eventos.entities.Event;
import com.unnoba.eventos.entities.Payment;
import com.unnoba.eventos.entities.User;
import com.unnoba.eventos.services.EventService;
import com.unnoba.eventos.services.PaymentService;
import com.unnoba.eventos.services.UserLoggedInService;


@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserLoggedInService uLoggedIn;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{id}")
    public String paymentNew(Model model, @PathVariable Long id) {
        model.addAttribute("event", eventService.findEvent(id));
        model.addAttribute("payment", new Payment());
        return "payments/new";
    }

    @PostMapping("/{eventId}")
    public String create(Model model, @PathVariable Long eventId, @ModelAttribute("payment") Payment payment) throws Exception {     // en vez de pasar un payment pasar event id y el user obtenerlo aca.
        try {
        	User u= uLoggedIn.loggedIn();
            Event e = eventService.findEvent(eventId);
            payment.setOwner(u);
            payment.setEvent(e);
            paymentService.createPayment(payment);
            return "registrations/confirmedRegistration";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

}
