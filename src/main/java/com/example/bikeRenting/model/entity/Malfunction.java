package com.example.bikeRenting.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "malfunction")
@NoArgsConstructor
@AllArgsConstructor
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
