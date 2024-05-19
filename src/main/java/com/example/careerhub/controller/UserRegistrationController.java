package com.example.careerhub.controller;


import com.example.careerhub.dto.*;
import com.example.careerhub.model.Category;
import com.example.careerhub.model.Job;
import com.example.careerhub.repository.UserRepository;
import com.example.careerhub.service.JobService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.careerhub.model.User;
import com.example.careerhub.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class UserRegistrationController {


    @Autowired
    private UserService userService;

    @Autowired
    JobService jobService;

    @Autowired
    UserRepository userRepository;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        List<Job> jobs = jobService.getAllJobs();
        Map<String, Long> jobsByCategory = jobs.stream()
                .collect(Collectors.groupingBy(Job::getCategory, Collectors.counting()));
        model.addAttribute("jobsByCategory", jobsByCategory);

        // Add category and icon path
        Map<String, String> categoryIconPaths = new HashMap<>();
        for (Category category : Category.values()) {
            categoryIconPaths.put(category.name(), category.getIconPath());
        }
        model.addAttribute("categoryIconPaths", categoryIconPaths);
        model.addAttribute("jobs", jobs);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserRegistrationDTO user = new UserRegistrationDTO();
        SeekerRegistrationDTO seeker = new SeekerRegistrationDTO();
        model.addAttribute("seeker", seeker);
        model.addAttribute("user", user);
        return "register";
    }

    @SuppressWarnings("null")
    @PostMapping("/registerUser/save")
    public String registration(@Valid @ModelAttribute("employer") UserRegistrationDTO userRegistrationDTO,
                               BindingResult result,
                               Model model, @RequestParam("imageRec") MultipartFile file,
                               @RequestParam("imgName") String imgName) {
        User existingUser = userService.findUserByEmail(userRegistrationDTO.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userRegistrationDTO);
            return "/register";
        }

        userService.saveUser(userRegistrationDTO, file, imgName);
        return "redirect:/register?success";
    }

    @GetMapping("/employer-home")
    public String getEmployerHome(Model model) {
        List<Job> jobs = jobService.getAllJobsWithUsers();
        Map<String, Long> jobsByCategory = jobs.stream()
                .collect(Collectors.groupingBy(Job::getCategory, Collectors.counting()));
        model.addAttribute("jobsByCategory", jobsByCategory);

        // Add category and icon path
        Map<String, String> categoryIconPaths = new HashMap<>();
        for (Category category : Category.values()) {
            categoryIconPaths.put(category.name(), category.getIconPath());
        }
        model.addAttribute("categoryIconPaths", categoryIconPaths);
        model.addAttribute("jobs", jobs);

        return "employer_home";
    }



    @GetMapping("/employer/settings")
    public String usersSettings(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "employer_settings";
    }



    @GetMapping("/employer/add-job")
    public String addJob(Model model) {
        model.addAttribute("jobDTO", new JobDTO());
        return "employer_job_offer_add";
    }


    @PostMapping("/employer/add-job")
    public String addJobPost(@ModelAttribute("jobDTO") JobDTO jobDTO, Principal principal) {
        String username = principal.getName();
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(username));
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User not found"));

        Job job = new Job();
        job.setId(jobDTO.getId());
        job.setTitle(jobDTO.getTitle());
        job.setCategory(jobDTO.getCategory());
        job.setType(jobDTO.getType());
        job.setExperience(jobDTO.getExperience());
        job.setSalary(jobDTO.getSalary());
        job.setRequirements(jobDTO.getRequirements());
        job.setLocation(jobDTO.getLocation());
        job.setDescription(jobDTO.getDescription());
        job.setUser(user);

        jobService.addJob(job);
        return "redirect:/job-offers";
    }

    @GetMapping("employer/view-job/{id}")
    public String viewJob(@PathVariable long id, Model model){
        model.addAttribute("job", jobService.getJobByID(id).get());
        return "employer_job_offer_details";
    }

    @GetMapping("employer/delete/{id}")
    public String viewJob(@PathVariable long id){
        jobService.deleteJob(id);
        return "redirect:/job-offers";
    }
    @GetMapping("employer/edit/{id}")
    public String editob(@PathVariable long id, Model model){
        model.addAttribute("job", jobService.getJobByID(id).get());
        return "employer_job_offer_edit";
    }


}
