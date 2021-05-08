package com.example.bikeRenting.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Bike bike;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Column(name = "reserved_till")
    private LocalDateTime reservedTill;

}
