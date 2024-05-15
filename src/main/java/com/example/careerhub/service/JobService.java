package com.example.careerhub.service;

import com.example.careerhub.model.Job;
import com.example.careerhub.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface JobService {

    public void addJob(Job job);

    public List<Job> getAllJobs();

    public Optional<Job> getJobByID(Long id);

    public void deleteJob(Long id);
}