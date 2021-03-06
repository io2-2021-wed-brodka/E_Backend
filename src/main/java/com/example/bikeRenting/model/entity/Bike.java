package com.example.bikeRenting.model.entity;

import com.example.bikeRenting.model.entity.enums.BikeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bike")
@Getter
@Setter
@NoArgsConstructor
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private BikeStatus status;

    @ManyToOne
    private BikeStation bikeStation;

    @OneToMany(mappedBy = "bike", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Rental>  rentals;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bike", orphanRemoval = true)
    private Reservation reservation;

    public Bike(Long id) {
        this.id = id;
    }

}
