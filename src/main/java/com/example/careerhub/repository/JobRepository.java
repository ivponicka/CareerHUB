package com.example.careerhub.repository;

import com.example.careerhub.model.Category;
import com.example.careerhub.model.Job;
import com.example.careerhub.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCategory(Category category);
    List<Job> findAll();
    @Query("SELECT j FROM Job j JOIN FETCH j.user")
    List<Job> findAllWithUsers();
    List<Job> findByUser(User user);
    Page<Job> findByCategory(String category, Pageable pageable);
}
