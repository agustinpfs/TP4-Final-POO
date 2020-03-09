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

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Invite;
import com.tp4Poo.entities.Payment;
import com.tp4Poo.entities.Registration;
import com.tp4Poo.entities.User;
import com.tp4Poo.services.EventService;
import com.tp4Poo.services.InviteService;
import com.tp4Poo.services.PaymentService;
import com.tp4Poo.services.SessionService;
import com.tp4Poo.services.UserService;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private EventService eventService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PaymentService paymentService;

    /* @GetMapping
    public String index(Model model){
        List<User> users = userService.users();
        model.addAttribute("users", users);
        return "users/index";
    }*/
    @GetMapping("/{id}")
    public String paymentNew(Model model, @PathVariable Long id) {
        model.addAttribute("event", eventService.find(id));
        model.addAttribute("payment", new Payment());
        return "payments/new";
    }

    @PostMapping("/{eventId}")
    public String create(Model model, @PathVariable Long eventId, @ModelAttribute("payment") Payment payment) throws Exception {     // en vez de pasar un payment pasar event id y el user obtenerlo aca.
        try {
            User u = sessionService.getCurrentUser();
            Event e = eventService.find(eventId);
            payment.setOwner(u);
            payment.setEvent(e);
            paymentService.create(payment);
            return "registrations/confirmedRegistration";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    /*@GetMapping("/{id}/delete")
    public String delete(@PathVariable ("id") Long id){
        userService.delete(id);
        return "redirect:/users";
    }
    
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model, Authentication authentication){
        User sessionUser = (User)authentication.getPrincipal();
        sessionUser.getEmail();
        User user = userService.find(id);
        model.addAttribute("user", user);
        return "users/edit";
    }
    
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,@ModelAttribute User user){
        userService.update(id,user);
        return "redirect:/users";
    }*/
}
