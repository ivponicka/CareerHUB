package com.example.careerhub.service;

import com.example.careerhub.model.Application;
import com.example.careerhub.model.Job;
import com.example.careerhub.model.Seeker;
import com.example.careerhub.repository.ApplicationRepository;
import com.example.careerhub.repository.JobRepository;
import com.example.careerhub.repository.SeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImp implements ApplicationService{
    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    SeekerRepository seekerRepository;
    @Autowired
    JobRepository jobRepository;
    @Override
    public void createApplication(Long seekerId, Long jobId) {
        Seeker seeker = seekerRepository.findById(seekerId).orElseThrow(()-> new RuntimeException("Invalid seeker ID: " + seekerId));
        Job job = jobRepository.findById(jobId).orElseThrow(()-> new RuntimeException("Invalid job ID: " + jobId));
        Application application = new Application();
        application.setDateOfApplication(new Date());
        application.setStatus("PENDING");
        application.setSeeker(seeker);
        application.setJob(job);

        applicationRepository.save(application);
    }

    @Override
    public List<Application> getApplicationsForSeeker(Long seekerId) {
        return applicationRepository.findBySeekerId(seekerId);
    }
}
