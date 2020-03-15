package com.tp4Poo.controllers;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp4Poo.entities.Event;
import com.tp4Poo.entities.Invite;
import com.tp4Poo.entities.User;
import com.tp4Poo.repositories.InviteRepository;
import com.tp4Poo.services.EventService;
import com.tp4Poo.services.InviteService;
import com.tp4Poo.services.UserLoggedInService;
import com.tp4Poo.services.UserService;


@Controller
@RequestMapping("/invites")
public class InviteController {

    @Autowired
    private UserService userS;

    @Autowired
    private EventService eventS;

    @Autowired
    private InviteService inviteS;
    
    @Autowired
    private InviteRepository inviteR;

    @Autowired
    private UserLoggedInService uLoggedInS;

    @GetMapping("/sendInv/{eventId}/{userId}")
    public String sendInv(Model model, @PathVariable Long eventId, @PathVariable Long userId) throws Exception {
        try {
            Event event = eventS.findEvent(eventId);
            if (event.getOwner().getId().equals(uLoggedInS.getCurrentUser().getId())) {
                Invite inv = new Invite();
                inv.setEvent(event);
                inv.setUser(userS.findUser(userId));
                inviteS.addInvite(inv);
                return "redirect:/invites/invite/{eventId}";
            }
            throw new Exception("Usuario inválido");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/invite/{eventId}")
    public String invite(Model model, @PathVariable Long eventId) throws Exception {
        try {
        	
        	
            if (eventS.findEvent(eventId).getOwner().getId().equals(uLoggedInS.getCurrentUser().getId())) {
                List<User> users = userS.retrieveAllUsers();
                model.addAttribute("users", users);
                model.addAttribute("eventId", eventId);
                return "invites/inviteUsers";
            }
            throw new Exception("Usuario inválido");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

    @GetMapping("/myInvites")
    public String invite(Model model) {
        User user = uLoggedInS.getCurrentUser();
        List<Invite> invites = inviteS.findInviteByUser(user);
        model.addAttribute("invites", invites);
        model.addAttribute("currentUser", uLoggedInS.getCurrentUser());
        return "invites/myInvites";
    }

    @GetMapping("/delete/{inviteId}")
    public String delete(Model model, @PathVariable Long inviteId, HttpServletRequest request) throws Exception {
        try {
            Invite inv = inviteS.findInvite(inviteId);
            if (uLoggedInS.getCurrentUser().getId().equals(inv.getUser().getId()) || uLoggedInS.getCurrentUser().getId().equals(inv.getEvent().getOwner().getId())) {   
                inviteR.deleteById(inviteId);
                String referer = request.getHeader("Referer");
                return "redirect:" + referer;
            }
            throw new Exception("Usuario inválido");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/error";
        }
    }

}
