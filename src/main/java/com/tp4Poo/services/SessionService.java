package com.tp4Poo.services;

import org.springframework.stereotype.Service;

import com.tp4Poo.entities.User;


@Service
public interface SessionService {
    
    public User getCurrentUser(); // Devuelve el usuario de la sesion.
    
}
