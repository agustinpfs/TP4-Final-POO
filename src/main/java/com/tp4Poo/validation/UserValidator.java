package com.tp4Poo.validation;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tp4Poo.entities.User;
import com.tp4Poo.services.UserService;



@Component(value = "SimpleUserValidator")
public class UserValidator implements IUserValidator {

    @Autowired
    private UserService userService;
    
    @Override
    public void validate(User user) throws Exception {
       
        if (userService.existe(user.getEmail()) == true) {
            throw new Exception("Ya existe un usuario registrado con este email!!");
        }
       
    }

    
}

