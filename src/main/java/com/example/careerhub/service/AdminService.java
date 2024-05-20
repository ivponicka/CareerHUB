package com.example.careerhub.service;
import com.example.careerhub.model.Admin;
public interface AdminService {
    Admin findByEmail(String email);
}
