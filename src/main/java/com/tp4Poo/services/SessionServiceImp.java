package com.tp4Poo.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.User;


@Service
public class SessionServiceImp implements SessionService{

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder
            .getContext()
            .getAuthentication();
        User u = (User) auth.getPrincipal();
        return u;
    }
     
}
