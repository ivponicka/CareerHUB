package com.example.careerhub.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private long id;
    private String companyName;

    private String noOfWorkers;

    private String category;
    private String location;
    private String imageName;

    private String description;
}
