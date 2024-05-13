package com.example.careerhub.controller;


import java.util.List;

import com.example.careerhub.dto.SeekerRegistrationDTO;
import com.example.careerhub.model.Job;
import com.example.careerhub.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.careerhub.dto.UserRegistrationDTO;
import com.example.careerhub.model.User;
import com.example.careerhub.service.UserService;

import jakarta.validation.Valid;


@Controller
public class UserRegistrationController {


    private UserService userService;

    @Autowired
    JobService jobService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

   @GetMapping("/login")
    public String login(){
        return "login";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
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
                               Model model){
        User existingUser = userService.findUserByEmail(userRegistrationDTO.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userRegistrationDTO);
            return "/register";
        }

        userService.saveUser(userRegistrationDTO);
        return "redirect:/register?success";
    }

    @GetMapping("/employer-home")
    public String getAdminHome(){
        return "employer_home";
    }

    @GetMapping("/job-offers")
    public String jobOffers(){
        return "employer_job_offers";
    }


    @GetMapping("/employer/add-job")
    public String addJob(){
        return "employer_add_job";
    }


    @GetMapping("/employer/add-job/add")
    public String addJob(Model model){
        model.addAttribute("job", new Job());
        return "employer_add_job";
    }


    @PostMapping("/employer/add-job/add")
    public String addJobPost(@ModelAttribute("job") Job job){
        jobService.addJob(job);
        return "redirect:employer_job_offers";
    }
}
