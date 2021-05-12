package com.example.bikeRenting.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "malfunction")
@Getter
@Setter
public class Malfunction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Bike bike;

    private boolean isFixed;
    private LocalDateTime detectionDate;
    private String description;

    @ManyToOne
    private User reportingUser;
}
