package com.example.careerhub.service;

import com.example.careerhub.dto.SeekerRegistrationDTO;
import com.example.careerhub.model.Seeker;

import java.util.List;
import java.util.Optional;


public interface SeekerService {
    void saveSeeker(SeekerRegistrationDTO seekerRegistrationDTO);
    void updateSeeker(Seeker seeker);
    Seeker findSeekerByEmail(String email);

    Optional<Seeker> findById(Long id);
    List<SeekerRegistrationDTO> findAllSeekers();
}
