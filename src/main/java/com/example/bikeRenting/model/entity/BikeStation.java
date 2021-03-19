package com.example.bikeRenting.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bikeStation")
@Getter
@Setter
public class BikeStation {

//    public enum BikeStationState {
//        Working, Blocked
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

//    private BikeStationState bikeStationState;

    private String locationName;
    private int maxBikes;

    @OneToMany(mappedBy = "bikeStation", cascade = CascadeType.ALL)
    private List<Bike> bikes;


}
