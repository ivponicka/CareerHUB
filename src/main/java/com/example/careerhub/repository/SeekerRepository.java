package com.example.careerhub.repository;


import com.example.careerhub.model.Seeker;
import com.example.careerhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SeekerRepository extends JpaRepository<Seeker, Integer>{
     Seeker findByEmail(String email);
     Optional<Seeker> findById(Long userId);
}
