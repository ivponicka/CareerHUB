package com.example.careerhub.service;
import com.example.careerhub.model.Category;
import com.example.careerhub.model.Job;
import com.example.careerhub.model.User;
import com.example.careerhub.repository.ApplicationRepository;
import com.example.careerhub.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class JobServiceImp implements JobService {
    @Autowired
    JobRepository jobRepository;
    @Autowired
    ApplicationRepository applicationRepository;
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
    public Page<Job> getPaginatedJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobRepository.findAll(pageable);
    }
    @Override
    public Page<Job> getPaginatedJobsByCategory(String category, int page, int size) {
        return jobRepository.findByCategory(category, PageRequest.of(page, size));
    }
    public List<Job> getJobsByUser(User user) {
        return jobRepository.findByUser(user);
    }
    @Override
    public List<Job> searchJobs(String name, String location) {
        if(name != null && !name.isEmpty() && location != null && !location.isEmpty()){
            return jobRepository.findByNameContainingAndLocationContaining(name, location);
        } else if (name != null && !name.isEmpty()){
            return  jobRepository.findByNameContaining(name);
        } else if (location != null && !location.isEmpty()){
            return  jobRepository.findByLocationContaining(location);
        } else  {
            return jobRepository.findAll();
        }
    }
    public boolean jobHasApplications(Long jobId) {
        return !applicationRepository.findByJobId(jobId).isEmpty();
    }
}
