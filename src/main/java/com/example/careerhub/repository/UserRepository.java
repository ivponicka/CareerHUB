package com.example.careerhub.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.careerhub.model.User;



public interface UserRepository<T extends User> extends JpaRepository<User, Integer>{
     User findByEmail(String email);
Optional<User> findById(Long userId);
}
