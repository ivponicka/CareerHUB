package com.example.careerhub.service;

import com.example.careerhub.model.Job;
import com.example.careerhub.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    @Autowired
    JobRepository jobRepository;

    public void addJob(Job job){
        jobRepository.save(job);
    }
}