package com.tp4Poo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp4Poo.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    public List<User> findUserByEmail(String email);
    
}
