package com.example.careerhub.service.CustomerDetailsService;

import com.example.careerhub.model.Admin;
import com.example.careerhub.model.Role;
import com.example.careerhub.model.User;
import com.example.careerhub.repository.AdminRepository;
import com.example.careerhub.repository.UserRepository;
import com.example.careerhub.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerAdminDetailsService implements UserDetailsService{

    private AdminRepository adminRepository;

    @Autowired
    AdminService adminService;

    public CustomerAdminDetailsService(UserRepository userRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminService.findByEmail(email);

        if (admin != null) {
            return new org.springframework.security.core.userdetails.User(admin.getEmail(),
                    admin.getPassword(),
                    mapRolesToAuthorities(admin.getRole()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private List<GrantedAuthority> mapRolesToAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }
}
