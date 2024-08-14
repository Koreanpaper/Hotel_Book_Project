package org.example.yuhgisuh_jah.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false)
    private String hotelname;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int count;

    @Column(unique = false)
    private LocalDate selDate;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> books = new ArrayList<>();


}
