package com.example.careerhub.service;

import com.example.careerhub.model.Category;
import com.example.careerhub.model.Job;
import com.example.careerhub.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class JobServiceImp implements JobService {

    @Autowired
    JobRepository jobRepository;

    @Override
    public void addJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Optional<Job> getJobByID(Long id) {
        return jobRepository.findById(id);
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public List<Job> getJobsByCategory(Category category) {
         return jobRepository.findByCategory(category);
    }

    @Override
    public List<Job> getAllJobsWithUsers() {
        return jobRepository.findAllWithUsers();
    }
}
