
package com.tp4Poo.services;




import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Payment;
import com.tp4Poo.entities.User;
import com.tp4Poo.repositories.PaymentRepository;
import com.tp4Poo.validation.PaymentValidator;
@Service
public class PaymentService{

    @Autowired
    private PaymentRepository paymentR;

    @Autowired
    private RegistrationService registrationS;

    @Autowired
    private PaymentValidator paymentV;

    
    public List<Payment> retrieveAllPayments() {
        return paymentR.findAll();
    }
    @Transactional(rollbackFor = Exception.class)
    public Payment addPayment(Payment payment) throws Exception {
        User user = payment.getOwner();
        Event event = payment.getEvent();
        if (event.getCost() > 0 && this.findPaymentByEventAndUser(event, user) == null) {
            paymentV.validate(payment);
            paymentR.save(payment);
            registrationS.addRegistration(payment.getEvent().getId());
            return payment;
        } else {
            throw new Exception("Error, ya se pago por el evento o el evento es gratis");
        }
    }
    
    public Payment findPayment(Long id) {
        return paymentR.findById(id).get();
    }

    
    public Payment replacePayment(Long id, Payment payment) {
        Payment p = paymentR.findById(id).get();
        p.setCardName(payment.getCardName());
        p.setCardNumber((payment.getCardNumber()));
        p.setOwner(payment.getOwner());
        p.setEvent(payment.getEvent());
        return paymentR.save(p);
    }

    
    public void deletePayment(Long id) {
        paymentR.deleteById(id);
    }

    
    public Payment findPaymentByEventAndUser(Event event, User user) {
        List<Payment> payments = paymentR.findByEventAndOwner(event, user);
        if (payments.isEmpty()) {
            return null;
        } else {
            return payments.get(0);
        }
    }

    
    public List<Payment> findPaymentsByEvent(Event event) {
        List<Payment> payments = paymentR.findByEvent(event);
        return payments;
    }

    
   

}
