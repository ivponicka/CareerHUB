package com.example.careerhub.controller;
import com.example.careerhub.model.Application;
import com.example.careerhub.model.Job;
import com.example.careerhub.model.Seeker;
import com.example.careerhub.model.User;
import com.example.careerhub.repository.ApplicationRepository;
import com.example.careerhub.service.JobService;
import com.example.careerhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
@Controller
public class JobController {
    @Autowired
    JobService jobService;
    @Autowired
    UserService userService;
    @Autowired
    private ApplicationRepository applicationRepository;
    @GetMapping("/recent-job-offers")
    public String  recentJobOffers(Model model, @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        Page<Job> jobPage = jobService.getPaginatedJobs(page, size);
        model.addAttribute("jobPage", jobPage);
        return "jobs_recent_job_offers";
    }

    @GetMapping("/job-offers")
    public String jobOffers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userService.findUserByEmail(currentUserName);
        List<Job> jobs = jobService.getJobsByUser(currentUser);
        model.addAttribute("jobs", jobs);
        return "employer_job_offers";
    }

    @GetMapping("/view-job/{id}")
    public String viewJob(@PathVariable long id, Model model){
        model.addAttribute("job", jobService.getJobByID(id).get());
        return "job_offer_details";
    }

    @GetMapping("/jobs_category_job_offers")
    public String getJobsByCategory(@RequestParam String category,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size, Model model) {
        Page<Job> jobPage = jobService.getPaginatedJobsByCategory(category, page, size);
        model.addAttribute("jobPage", jobPage);
        model.addAttribute("category", category);
        return "jobs_category_job_offers";
    }

    @GetMapping("/search-jobs")
    public String searchJobs(@RequestParam(required = false) String name, @RequestParam(required = false) String location, Model model){
        List<Job> jobs = jobService.searchJobs(name, location);
        model.addAttribute("jobs", jobs);
        return "jobs_search_job_offers";
    }

    @GetMapping("/employer/application-details/{id}")
    public String applicationDetails(@PathVariable long id, Model model){
        Application applications = applicationRepository.findById(id).get();
        Job job = applications.getJob();
        Seeker seeker = applications.getSeeker();
        model.addAttribute("job", jobService.getJobByID(id).get());
        model.addAttribute("seeker", seeker);
        model.addAttribute("applications", applications);
        return "employer_application_details";
    }

    @PostMapping("/employer/send-message")
    public String sendMessage(@RequestParam Long applicationId, @RequestParam String message, @RequestParam String status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setMessage(message);
        application.setStatus(status);
        applicationRepository.save(application);
        return "redirect:/employer/applications";
    }
}
