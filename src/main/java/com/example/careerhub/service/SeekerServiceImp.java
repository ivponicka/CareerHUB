package com.example.careerhub.service;
import com.example.careerhub.dto.SeekerRegistrationDTO;
import com.example.careerhub.dto.UserRegistrationDTO;
import com.example.careerhub.model.Role;
import com.example.careerhub.model.Seeker;
import com.example.careerhub.model.User;
import com.example.careerhub.repository.RoleRepository;
import com.example.careerhub.repository.SeekerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class SeekerServiceImp implements SeekerService {
    private SeekerRepository seekerRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    public SeekerServiceImp(SeekerRepository seekerRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder) {
        this.seekerRepository = seekerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void saveSeeker(SeekerRegistrationDTO seekerRegistrationDTO) {
        Seeker seeker = new Seeker();
        seeker.setFirstName(seekerRegistrationDTO.getFirstName());
        seeker.setLastName(seekerRegistrationDTO.getLastName());
        seeker.setEmail(seekerRegistrationDTO.getEmail());
        seeker.setPhone(seekerRegistrationDTO.getPhone());
        seeker.setPassword(passwordEncoder.encode(seekerRegistrationDTO.getPassword()));
        Role role = roleRepository.findByName("SEEKER");
        if(role == null){
            role = checkRoleExist();
        }
        seeker.setRoles(Arrays.asList(role));
        seekerRepository.save(seeker);
    }
    public Seeker findSeekerByEmail(String email) {
        return seekerRepository.findByEmail(email);
    }
    @Override
    public List<SeekerRegistrationDTO> findAllSeekers() {
        List<Seeker> seekers = seekerRepository.findAll();
        return seekers.stream()
                .map((seeker) -> mapToUserDto(seeker))
                .collect(Collectors.toList());
    }
    private SeekerRegistrationDTO mapToUserDto(Seeker seeker){
        SeekerRegistrationDTO seekerRegistrationDTO = new SeekerRegistrationDTO();
        seekerRegistrationDTO.getFirstName();
        seekerRegistrationDTO.getFirstName();
        seekerRegistrationDTO.setEmail(seeker.getEmail());
        return seekerRegistrationDTO;
    }
    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("SEEKER");
        return roleRepository.save(role);
    }
}