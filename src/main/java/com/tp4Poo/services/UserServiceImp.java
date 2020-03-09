package com.tp4Poo.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.User;
import com.tp4Poo.repositories.UserRepository;


@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    @Autowired
//    private UserValidator userValidator;

    @Override
    public List<User> users() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) throws Exception {
//        userValidator.validate(user);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User find(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User update(Long id, User user) {
        User u = userRepository.findById(id).get();
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        return userRepository.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).get(0);

    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existe(String email) {
        return !userService.findByEmail(email).isEmpty();
    }

}
