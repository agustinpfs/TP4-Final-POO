package com.tp4Poo.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.User;
import com.tp4Poo.repositories.UserRepository;
import com.tp4Poo.validation.UserValidator;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userR;

    @Autowired
    private UserService userS;

    @Autowired
    private UserValidator userV;

    public List<User> retrieveAllUsers() {
        return userR.findAll();
    }

    public User addUser(User user) throws Exception {
        userV.validate(user);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userR.save(user);
    }

    public User findUser(Long id) {
        return userR.findById(id).get();
    }

    public User replaceUser(Long id, User user) {
        User u = userR.findById(id).get();
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        return userR.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userR.findUserByEmail(username).get(0);

    }
    public User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return user;
    }
    public void deleteUser(Long id) {
        userR.deleteById(id);
    }

    public List<User> findUserByEmail(String email) {
        return userR.findUserByEmail(email);
    }

    public boolean exist(String email) {
        return !userS.findUserByEmail(email).isEmpty();
    }

}
