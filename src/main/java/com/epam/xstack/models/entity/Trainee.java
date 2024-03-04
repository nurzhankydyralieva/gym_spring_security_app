package com.epam.xstack.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "trainee")
public class Trainee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainee_id")
    private UUID id;
    @ManyToMany(mappedBy = "traineeList", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Trainer> trainers = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trainee", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Training> trainings;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Column(name = "address")
    private String address;
}
