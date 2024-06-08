package com.example.careerhub.service;
import com.example.careerhub.repository.JobRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.careerhub.dto.UserRegistrationDTO;
import com.example.careerhub.model.Role;
import com.example.careerhub.model.User;
import com.example.careerhub.repository.RoleRepository;
import com.example.careerhub.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images";
    @Override
    public void saveUser(UserRegistrationDTO userRegistrationDTO, MultipartFile file, String imgName) {
        User user = new User();
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPhone(userRegistrationDTO.getPhone());
        user.setCompanyName(userRegistrationDTO.getCompanyName());
        user.setLocation(userRegistrationDTO.getLocation());
        user.setCompanyDescription(userRegistrationDTO.getCompanyDescription());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        Role role = roleRepository.findByName("USER");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = imgName + "-" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());
                user.setImageName(fileName);
                user.setImagePath(filePath.toString());
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
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
    @Override
    public List<User> getAllUsers() {
           return userRepository.findAll();
    }
    @Override
    public Optional<User> findUserByID(Long id) {
       return userRepository.findById(id);
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
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return userRepository.findByEmail(email);
        } else {
            // Handle the case when no user is authenticated
            return null;
        }
    }

}