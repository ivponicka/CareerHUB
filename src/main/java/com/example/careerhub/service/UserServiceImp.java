package com.example.careerhub.service;


import com.example.careerhub.model.Job;
import com.example.careerhub.repository.JobRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.careerhub.dto.UserRegistrationDTO;
import com.example.careerhub.model.Role;
import com.example.careerhub.model.User;
import com.example.careerhub.repository.RoleRepository;
import com.example.careerhub.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JobRepository jobRepository;

    public UserServiceImp(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPhone(userRegistrationDTO.getPhone());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        Role role = roleRepository.findByName("USER");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserRegistrationDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }



    private UserRegistrationDTO mapToUserDto(User user){
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
       
        userRegistrationDTO.getFirstName();
        userRegistrationDTO.getFirstName();
        userRegistrationDTO.setEmail(user.getEmail());
        return userRegistrationDTO;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }


}