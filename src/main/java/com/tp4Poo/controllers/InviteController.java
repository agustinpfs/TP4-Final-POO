/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp4Poo.controllers;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unnoba.eventos.entities.Event;
import com.unnoba.eventos.entities.Invite;
import com.unnoba.eventos.entities.User;
import com.unnoba.eventos.services.EventService;
import com.unnoba.eventos.services.InviteService;
import com.unnoba.eventos.services.UserLoggedInService;
import com.unnoba.eventos.services.UserService;

/**
 *
 * @author guillermo
 */
@Controller
@RequestMapping("/invites")
public class InviteController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private InviteService inviteService;

    @Autowired
    private UserLoggedInService uLoggedIn;
    
    @GetMapping("/{eventId}/{userId}/sendInv")
    public String sendInv(Model model, @PathVariable Long eventId, @PathVariable Long userId) throws Exception {
        try {
        	User u= uLoggedIn.loggedIn();
            Event event = eventService.findEvent(eventId);
            if (event.getOwner().getId().equals(u.getId())) {
                Invite i = new Invite();
                i.setEvent(event);
                i.setUser(userService.findUser(userId));
                inviteService.createInvite(i);
                return "redirect:/invites/{eventId}/invite";
            }
            throw new Exception("Permiso denegado usuario invalido");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/{eventId}/invite")
    public String invite(Model model, @PathVariable Long eventId) throws Exception {
        try {
        	User u= uLoggedIn.loggedIn();
            if (eventService.findEvent(eventId).getOwner().getId().equals(u.getId())) {
                List<User> users = userService.retrieveAllUsers();
                model.addAttribute("users", users);
                model.addAttribute("eventId", eventId);
                return "invites/inviteUsers";
            }
            throw new Exception("Invalid User");
        } catch (Exception e) {
            model.addAttribute(e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/myInvites")
    public String invite(Model model) {
    	User u= uLoggedIn.loggedIn();
        List<Invite> invites = inviteService.findInviteByUser(u);
        model.addAttribute("invites", invites);
        model.addAttribute("currentUser", u);
        return "invites/myInvites";
    }

    @GetMapping("/{inviteId}/delete")
    public String delete(Model model, @PathVariable Long inviteId, HttpServletRequest request) throws Exception {
        try {
        	User u= uLoggedIn.loggedIn();
            Invite temp = inviteService.findInvite(inviteId);
            if ((temp.getUser().getId().equals(u)) || (temp.getEvent().getOwner().getId().equals(u))) {
                inviteService.deleteInvite(inviteId);
                String referer = request.getHeader("Referer");
                return "redirect:" + referer;
            }
            throw new Exception("invalid User");
        } catch (Exception e) {
            model.addAttribute(e.getMessage());
            return "/error/error";
        }
    }

}
