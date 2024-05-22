package com.example.careerhub.service.CustomerDetailsService;

import com.example.careerhub.dto.SeekerRegistrationDTO;
import com.example.careerhub.model.Role;
import com.example.careerhub.model.Seeker;
import com.example.careerhub.model.User;
import com.example.careerhub.repository.SeekerRepository;
import com.example.careerhub.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerSeekerDetailsService implements UserDetailsService{

    private SeekerRepository seekerRepository;

    public CustomerSeekerDetailsService(SeekerRepository seekerRepository) {
        this.seekerRepository = seekerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Seeker seeker = seekerRepository.findByEmail(email);

        if (seeker != null) {
            return new CustomSeekerDetails(
                    seeker.getId(),
                    seeker.getEmail(),
                    seeker.getPassword(),
                    mapRolesToAuthorities(seeker.getRoles())
            );
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private List<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
