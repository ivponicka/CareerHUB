package com.example.careerhub.service;

import com.example.careerhub.dto.SeekerRegistrationDTO;
import com.example.careerhub.dto.UserRegistrationDTO;
import com.example.careerhub.model.Seeker;
import com.example.careerhub.model.User;

import java.util.List;


public interface SeekerService {
    void saveSeeker(SeekerRegistrationDTO seekerRegistrationDTO);

    Seeker findSeekerByEmail(String email);

    List<SeekerRegistrationDTO> findAllSeekers();
}
