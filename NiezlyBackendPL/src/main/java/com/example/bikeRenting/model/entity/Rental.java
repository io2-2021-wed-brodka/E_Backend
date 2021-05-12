package com.example.bikeRenting.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
@Data
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Bike bike;

    @ManyToOne
    private User user;

    @ManyToOne
    private BikeStation fromStation;

    @ManyToOne
    private BikeStation toStation;

    private LocalDateTime startDate;
    private LocalDateTime endDate;


}
