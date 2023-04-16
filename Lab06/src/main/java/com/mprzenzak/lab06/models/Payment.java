package com.mprzenzak.lab06.models;

import com.mprzenzak.lab06.enums.ServiceType;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date paymentDate;
    private int amount;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Invoice payment;
}