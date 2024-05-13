package com.example.careerhub.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class JobDTO {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    private String category;
    private String title;
    private String type;

    private String salary;
    private String requirements;
    private int experience;
    private String location;
    private String description;
}
