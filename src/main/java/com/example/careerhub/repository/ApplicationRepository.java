package com.example.careerhub.repository;
import com.example.careerhub.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findBySeekerId(long id);
    List<Application> findByJobIdIn(List<Long> jobIds);
    List<Application> findByJobId(Long jobId);
    List<Application> findAllByJobUserId(Long userId);
}
