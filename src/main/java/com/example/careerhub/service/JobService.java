package com.example.careerhub.service;

import com.example.careerhub.model.Category;
import com.example.careerhub.model.Job;
import com.example.careerhub.model.User;
import com.example.careerhub.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface JobService {


    public void addJob(Job job);

    public List<Job> getAllJobs();

    public Optional<Job> getJobByID(Long id);

    public void deleteJob(Long id);

    public List<Job> getJobsByCategory(Category category);

    public List<Job> getAllJobsWithUsers();

    public Page<Job> getPaginatedJobs(int page, int size);
    Page<Job> getPaginatedJobsByCategory(String category, int page, int size);

    public List<Job> getJobsByUser(User user);

}