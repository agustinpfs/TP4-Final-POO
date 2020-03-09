package com.tp4Poo.repositories;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Payment;
import com.tp4Poo.entities.User;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
    
    public List<Payment> findByEventAndOwner(Event event, User owner);

    public List<Payment> findByEvent(Event event);
     
}
