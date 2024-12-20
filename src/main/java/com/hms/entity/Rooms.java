package com.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="type" , nullable = false)
    private String type;

    @Column(name="count" , nullable = false)
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "per_night_price", nullable = false)
    private Double perNightPrice;

    @Version
    private long versions;

}