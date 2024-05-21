package com.example.careerhub.controller;


import com.example.careerhub.dto.SeekerRegistrationDTO;
import com.example.careerhub.dto.UserRegistrationDTO;
import com.example.careerhub.model.Category;
import com.example.careerhub.model.Job;
import com.example.careerhub.model.Seeker;
import com.example.careerhub.model.User;
import com.example.careerhub.repository.SeekerRepository;
import com.example.careerhub.service.JobService;
import com.example.careerhub.service.SeekerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class SeekerController {


    private SeekerService seekerService;

    @Autowired
    JobService jobService;

    @Autowired
    SeekerRepository seekerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SeekerController(SeekerService seekerService) {
        this.seekerService = seekerService;
    }



    @SuppressWarnings("null")
    @PostMapping("/registerSeeker/save")
    public String registration(@Valid @ModelAttribute("seeker") SeekerRegistrationDTO seekerRegistrationDTO,
                               BindingResult result,
                               Model model){
        Seeker existingSeeker = seekerService.findSeekerByEmail(seekerRegistrationDTO.getEmail());

        if(existingSeeker != null && existingSeeker.getEmail() != null && !existingSeeker.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("seeker", seekerRegistrationDTO);
            return "/register";
        }

        seekerService.saveSeeker(seekerRegistrationDTO);
        return "redirect:/register?success";
    }

    @GetMapping("/seeker-home")
    public String seekerHome(Model model){
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
        return "seeker_home";
    }
    @GetMapping("/seeker/settings")
    public String usersSettings(Model model, Principal principal) {
        String username = principal.getName();
        Seeker seekerData = seekerService.findSeekerByEmail(username);
        model.addAttribute("seekerData", seekerData);
        return "seeker_settings";
    }

    @PostMapping("/seeker/settings/update")
    public String updateUser(@ModelAttribute("userData") SeekerRegistrationDTO seekerRegistrationDTO) throws IOException {
        Seeker existingSeeker = seekerRepository.findByEmail(seekerRegistrationDTO.getEmail());
        existingSeeker.setFirstName(seekerRegistrationDTO.getFirstName());
        existingSeeker.setLastName(seekerRegistrationDTO.getLastName());
        existingSeeker.setEmail(seekerRegistrationDTO.getEmail());


        if (seekerRegistrationDTO.getPassword() != null && !seekerRegistrationDTO.getPassword().isEmpty()) {
            existingSeeker.setPassword(passwordEncoder.encode(seekerRegistrationDTO.getPassword()));
        }

        existingSeeker.setPhone(seekerRegistrationDTO.getPhone());

        seekerService.updateSeeker(existingSeeker);
        return "redirect:/seeker/settings";
    }
}
