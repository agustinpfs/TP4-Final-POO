package com.tp4Poo.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.tp4Poo.entities.User;


@Service
public interface UserService {
    
    public List<User> users();
    
    public User create(User user) throws Exception;
    
    public User find(Long id);
    
    public User update(Long id,User user);

    public void delete(Long id);
    
    public List<User> findByEmail(String email);
    
    public boolean existe (String email);
    
}
