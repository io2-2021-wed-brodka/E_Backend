package com.example.bikeRenting.model.entity;

import com.example.bikeRenting.model.entity.enums.BikeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user_data")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String password;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Rental> rentedBikes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Reservation> reservations;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public User(Long id) {
        this.id = id;
    }
}
