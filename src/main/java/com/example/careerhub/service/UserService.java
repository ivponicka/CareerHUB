package com.example.careerhub.service;

import java.util.List;

import com.example.careerhub.dto.UserRegistrationDTO;
import com.example.careerhub.model.Job;
import com.example.careerhub.model.User;


public interface UserService  {
    void saveUser(UserRegistrationDTO userRegistrationDTO);

    User findUserByEmail(String email);

    List<UserRegistrationDTO> findAllUsers();


}
