package com.example.careerhub.service;

import com.example.careerhub.dto.SeekerRegistrationDTO;
import com.example.careerhub.dto.UserRegistrationDTO;
import com.example.careerhub.model.Seeker;
import com.example.careerhub.model.User;
import com.example.careerhub.repository.SeekerRepository;

import java.util.List;


public interface SeekerService {
    void saveSeeker(SeekerRegistrationDTO seekerRegistrationDTO);
    void updateSeeker(Seeker seeker);
    Seeker findSeekerByEmail(String email);

    List<SeekerRegistrationDTO> findAllSeekers();
}
