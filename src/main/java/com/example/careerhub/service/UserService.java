package com.example.careerhub.service;
import java.util.List;
import java.util.Optional;

import com.example.careerhub.dto.UserRegistrationDTO;
import com.example.careerhub.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
public interface UserService  {
    void saveUser(UserRegistrationDTO userRegistrationDTO, MultipartFile file, String imgName);
    User findUserByEmail(String email);
    List<UserRegistrationDTO> findAllUsers();
    public List<User> getAllUsers();
    Optional<User> findUserByID(Long id);
}
