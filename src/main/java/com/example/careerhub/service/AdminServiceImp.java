package com.example.careerhub.service;
import com.example.careerhub.model.Admin;
import com.example.careerhub.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AdminServiceImp implements AdminService{
    @Autowired
    AdminRepository adminRepository;
    @Override
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
}
