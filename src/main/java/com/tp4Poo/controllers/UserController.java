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

import com.tp4Poo.entities.User;
import com.tp4Poo.services.SessionService;
import com.tp4Poo.services.UserService;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model) {
        List<User> users = userService.users();
        model.addAttribute("users", users);
        return "users/index";
    }

    @GetMapping("/new")
    public String userNew(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping
    public String create(Model model, @ModelAttribute User user) throws Exception {
        try {
            userService.create(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable("id") Long id) throws Exception {
        try {

            if (Objects.equals(sessionService.getCurrentUser().getId(), id)) {
                userService.delete(id);
                return "redirect:/users";
            }
            throw new Exception("Permiso denegado usuario invalido");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model, Authentication authentication) throws Exception {
        try{
        User sessionUser = (User) authentication.getPrincipal();
        if (Objects.equals(sessionUser.getId(), id)) {
            User user = userService.find(id);
            model.addAttribute("user", user);
            return "users/edit";
        }
        throw new Exception("Permiso denegado usuario invalido");
    }
         catch (Exception e) {
             model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @PostMapping("/{id}/update")
    public String update(Model model, @PathVariable Long id, @ModelAttribute User user) throws Exception {
        try{
        if (Objects.equals(sessionService.getCurrentUser().getId(), id)) {
            userService.update(id, user);
            return "redirect:/users";
        }
        throw new Exception("Permiso denegado usuario invalido");
    }
         catch (Exception e) {
             model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

}
