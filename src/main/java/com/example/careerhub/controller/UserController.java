package com.example.careerhub.controller;


import com.example.careerhub.dto.*;
import com.example.careerhub.model.Category;
import com.example.careerhub.model.Job;
import com.example.careerhub.repository.UserRepository;
import com.example.careerhub.service.CustomerDetailsService.CustomUserDetails;
import com.example.careerhub.service.JobService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.careerhub.model.User;
import com.example.careerhub.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    JobService jobService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images";
    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        List<Job> jobs = jobService.getAllJobs();
        Map<String, Long> jobsByCategory = jobs.stream()
                .collect(Collectors.groupingBy(Job::getCategory, Collectors.counting()));
        model.addAttribute("jobsByCategory", jobsByCategory);
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
        UserRegistrationDTO user = new UserRegistrationDTO();
        SeekerRegistrationDTO seeker = new SeekerRegistrationDTO();
        model.addAttribute("seeker", seeker);
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("about")
    public String aboutUs(){
        return "about";
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

    @PostMapping("/employer/settings/update")
    public String updateUser(@ModelAttribute("userData") UserRegistrationDTO userRegistrationDTO, @RequestParam("imageRec")MultipartFile file, @RequestParam("imgName") String imgName) throws IOException {
        User existingUser = userRepository.findByEmail(userRegistrationDTO.getEmail());
        existingUser.setFirstName(userRegistrationDTO.getFirstName());
        existingUser.setLastName(userRegistrationDTO.getLastName());
        existingUser.setEmail(userRegistrationDTO.getEmail());
        existingUser.setLocation(userRegistrationDTO.getLocation());

        if (userRegistrationDTO.getPassword() != null && !userRegistrationDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        }


        existingUser.setCompanyDescription(userRegistrationDTO.getCompanyDescription());
        existingUser.setCompanyName(userRegistrationDTO.getCompanyName());
        existingUser.setPhone(userRegistrationDTO.getPhone());
        String imageUUID;

        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = imgName;

        }
        existingUser.setImageName(imageUUID);
        userService.updateUser(existingUser);
        return "redirect:/employer/settings";
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
    public String usersSettings(Model model, Principal principal) {
        String username = principal.getName();
        User userData = userService.findUserByEmail(username);
        model.addAttribute("userData", userData);
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
        job.setName(jobDTO.getName());
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
