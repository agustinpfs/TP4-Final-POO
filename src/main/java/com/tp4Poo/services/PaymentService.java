package com.tp4Poo.services;


import java.util.List;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Payment;
import com.tp4Poo.entities.User;


@Service
public interface PaymentService {
    
    public List<Payment> users();
    
    public Payment create(Payment payment) throws Exception;
    
    public Payment findByEventAndUser(Event event, User user);
    
    public List<Payment> findByEvent(Event event);
    
    public Payment find(Long id);
    
    public Payment update(Long id,Payment payment);

    public void delete(Long id);
    
}
