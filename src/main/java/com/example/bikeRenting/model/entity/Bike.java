package com.example.bikeRenting.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bike")
@Getter
@Setter
public class Bike {

//    public enum BikeState {
//        Working, InService, Blocked
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    private BikeState bikeState;

    @ManyToOne
    private BikeStation bikeStation;

    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL)
    private Set<Rental>  rentals;

}
