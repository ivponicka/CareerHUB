package com.example.careerhub.service;

import com.example.careerhub.model.Application;
import com.example.careerhub.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ApplicationService {


    public void createApplication(Long seekerId, Long jobId);
    public List<Application> getApplicationsForSeeker(Long seekerId);
    Application getApplicationById(long id);
    public List<Application> findByJobIds(List<Long> jobIds);
}
