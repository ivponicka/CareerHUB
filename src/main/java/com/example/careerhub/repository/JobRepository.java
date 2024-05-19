package com.example.careerhub.repository;

import com.example.careerhub.model.Category;
import com.example.careerhub.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCategory(Category category);
    List<Job> findAll();
}
