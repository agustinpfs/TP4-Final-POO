/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp4Poo.services;




import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Payment;
import com.tp4Poo.entities.User;
import com.tp4Poo.repositories.PaymentRepository;

/**
 *
 * @author root
 */
@Service
public class PaymentServiceImp implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RegistrationService registrationService;

//    @Autowired
//    private PaymentValidator paymentValidator;

    @Override
    public List<Payment> users() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment find(Long id) {
        return paymentRepository.findById(id).get();
    }

    @Override
    public Payment update(Long id, Payment payment) {
        Payment p = paymentRepository.findById(id).get();
        p.setCardName(payment.getCardName());
        p.setCardNumber((payment.getCardNumber()));
        p.setOwner(payment.getOwner());
        p.setEvent(payment.getEvent());
        return paymentRepository.save(p);
    }

    @Override
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public Payment findByEventAndUser(Event event, User user) {
        List<Payment> payments = paymentRepository.findByEventAndOwner(event, user);
        if (payments.isEmpty()) {
            return null;
        } else {
            return payments.get(0);
        }
    }

    @Override
    public List<Payment> findByEvent(Event event) {
        List<Payment> payments = paymentRepository.findByEvent(event);
        return payments;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Payment create(Payment payment) throws Exception {
        User user = payment.getOwner();
        Event event = payment.getEvent();
        if (event.getCost() > 0 && this.findByEventAndUser(event, user) == null) {
//            paymentValidator.validate(payment);
            paymentRepository.save(payment);
            registrationService.create(payment.getEvent().getId());
            return payment;
        } else {
            throw new Exception("Error, ya se pago por el evento o el evento es gratis");
        }
    }

}
