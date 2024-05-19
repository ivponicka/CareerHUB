package com.example.careerhub.controller;

import com.example.careerhub.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobController {
    @Autowired
    JobService jobService;

}
