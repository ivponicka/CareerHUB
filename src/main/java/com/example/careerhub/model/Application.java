package com.example.careerhub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_of_application")
    private Date dateOfApplication;
    private String status;
    @Column(name = "response_date")
    private Date responseDate;

    @Column(name = "message", length = 1000)
    private String message;

    @ManyToOne
    @JoinColumn(name = "seeker_id", nullable = false)
    private Seeker seeker;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

}
