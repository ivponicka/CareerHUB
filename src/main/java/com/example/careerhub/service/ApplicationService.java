package com.example.careerhub.service;
import com.example.careerhub.model.Application;
import java.util.List;

public interface ApplicationService {
    public void createApplication(Long seekerId, Long jobId);
    public List<Application> getApplicationsForSeeker(Long seekerId);
}
