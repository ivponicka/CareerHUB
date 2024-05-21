package com.example.careerhub.controller;


import com.example.careerhub.dto.SeekerRegistrationDTO;
import com.example.careerhub.model.Seeker;
import com.example.careerhub.service.SeekerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SeekerController {


    private SeekerService seekerService;

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



}
