package com.tp4Poo.repositories;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Registration;
import com.tp4Poo.entities.User;


@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>{

    public List<Registration> findByEventAndUser(Event event, User user);

    public List<Registration> findByUserId(Long user_id);

    public List<Registration> findByEvent(Event event);
       
}
