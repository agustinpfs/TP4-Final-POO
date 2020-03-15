package com.tp4Poo.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp4Poo.entities.User;
import com.tp4Poo.services.UserLoggedInService;
import com.tp4Poo.services.UserService;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserLoggedInService uLoggedInS;

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model) {
        List<User> users = userService.retrieveAllUsers();
        model.addAttribute("users", users);
        return "users/index";
    }

    @GetMapping("/new")
    public String userNew(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping
    public String addUser(Model model, @ModelAttribute User user) throws Exception {
        try {
            userService.addUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable("id") Long id) throws Exception {
        try {

            if (uLoggedInS.getCurrentUser().getId().equals(id)) {
                userService.deleteUser(id);
                return "redirect:/users";
            }
            throw new Exception("Usuario inválido");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model, Authentication authentication) throws Exception {
        try{
        if (uLoggedInS.getCurrentUser().getId().equals(id)) {
            User user = userService.findUser(id);
            model.addAttribute("user", user);
            return "users/edit";
        }
        throw new Exception("Usuario inválido");
    }
         catch (Exception e) {
             model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @PostMapping("/{id}/update")
    public String update(Model model, @PathVariable Long id, @ModelAttribute User user) throws Exception {
        try{
        if (uLoggedInS.getCurrentUser().getId().equals(id)) {
            userService.replaceUser(id, user);
            return "redirect:/users";
        }
        throw new Exception("Usuario inválido");
    }
         catch (Exception e) {
             model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

}
