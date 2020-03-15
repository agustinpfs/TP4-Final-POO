package com.tp4Poo.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tp4Poo.entities.User;
import com.tp4Poo.services.UserService;

@Component(value = "SimpleUserValidator")
public class UserValidator {

    @Autowired
    private UserService userService;
    public void validate(User user) throws Exception {
       
        if (userService.exist(user.getEmail()) == true) {
            throw new Exception("Ya existe un usuario registrado con este email!!");
        }
       
    }

    
}

