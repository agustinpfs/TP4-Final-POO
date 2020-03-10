/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**package com.unnoba.eventos.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unnoba.eventos.entities.User;
import com.unnoba.eventos.services.UserService;

import com.unnoba.eventos.services.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserLoggedInService uLoggedIn;
    @Autowired
    private UserService userS;

    @GetMapping
    public String index(Model model) {
        List<User> users = userS.retrieveAllUsers();
        model.addAttribute("users", users);
        return "users/index";
    }

    @GetMapping("/new")
    public String userNew(Model model) {
    	User u = new User();
        model.addAttribute("user", u);
        return "users/new";
    }

    @PostMapping
    public String addUser(Model model, @ModelAttribute User user) throws Exception {
        try {
            userS.createUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(Model model, @PathVariable("id") Long id) throws Exception {
        try {
        	User u= uLoggedIn.loggedIn();
            if(u.getId().equals(id)) {
                userService.deleteUser(id);
                return "redirect:/users";
            }
            throw new Exception("Invalid User");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable Long id, Model model, Authentication authentication) throws Exception {
        try{
        	User u= uLoggedIn.loggedIn();
        if (u.getId().equals(id)) {
            User user = userService.findUser(id);
            model.addAttribute("user", user);
            return "users/edit";
        }
        throw new Exception("Invalid User");
    }
         catch (Exception e) {
             model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @PostMapping("/{id}/update")
    public String updateUser(Model model, @PathVariable Long id, @ModelAttribute User user) throws Exception {
        try{
        	User u= uLoggedIn.loggedIn();
        if (u.getId().equals(id)) {
            userService.updateUser(id, user);
            return "redirect:/users";
        }
        throw new Exception("Invalid User");
    }
         catch (Exception e) {
             model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

}

**/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp4Poo.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unnoba.eventos.entities.User;

import com.unnoba.eventos.services.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserLoggedInService uLoggedIn;
    @Autowired
    private UserService userS;

    @GetMapping
    public String index(Model model) {
        List<User> users = userS.retrieveAllUsers();
        model.addAttribute("users", users);
        return "users/index";
    }

    @GetMapping("/new")
    public String userNew(Model model) {
    	User u = new User();
        model.addAttribute("user", u);
        return "users/new";
    }

    @PostMapping
    public String addUser(Model model, @ModelAttribute User user) throws Exception {
        try {
            userS.createUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(Model model, @PathVariable("id") Long id) throws Exception {
        try {
        	User u= uLoggedIn.loggedIn();
            if(u.getId().equals(id)) {
                userS.deleteUser(id);
                return "/users";
            }
            throw new Exception("Invalid User");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model, Authentication authentication) throws Exception {
        try{
        	User u= uLoggedIn.loggedIn();
        if (u.getId().equals(id)) {
            User user = userS.findUser(id);
            model.addAttribute("user", user);
            return "users/edit";
        }
        throw new Exception("Invalid User");
    }
         catch (Exception e) {
             model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @PostMapping("/update/{id}")
    public String updateUser(Model model, @PathVariable Long id, @ModelAttribute User user) throws Exception {
        try{
        	User u= uLoggedIn.loggedIn();
        if (u.getId().equals(id)) {
            userS.updateUser(id, user);
            return "/users";
        }
        throw new Exception("Invalid User");
    }
         catch (Exception e) {
             model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

}
