package com.example.careerhub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String title;
    private String type;

    private String salary;
    @Column(length = 1000)
    private String requirements;

    private int experience;
    private String location;
    @Column(length = 1000)
    private String description;

    // Define ManyToOne relationship with User entity
    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY is used for better performance
    @JoinColumn(name = "user_id", nullable = false) // Define the foreign key column
    private User user; // Reference to the User who posted this job
}
