package com.tp4Poo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp4Poo.entities.Payment;
import com.tp4Poo.services.EventService;
import com.tp4Poo.services.PaymentService;
import com.tp4Poo.services.UserLoggedInService;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private EventService eventS;

    @Autowired
    private UserLoggedInService uLoggedInS;

    @Autowired
    private PaymentService paymentS;

    @GetMapping("/{id}")
    public String addPayment(Model model, @PathVariable Long id) {
        model.addAttribute("event", eventS.findEvent(id));
        model.addAttribute("payment", new Payment());
        return "payments/newPayment";
    }

    @PostMapping("/{eventId}")
    public String createPayment(Model model, @PathVariable Long eventId, @ModelAttribute("payment") Payment payment) throws Exception {
        try {
            payment.setOwner(uLoggedInS.getCurrentUser());
            payment.setEvent(eventS.findEvent(eventId));
            paymentS.addPayment(payment);
            return "registrations/confirmedRegistration"; //La Inscripción se realizó correctamente
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }
}
