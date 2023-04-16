package com.mprzenzak.lab06.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date paymentDate;
    private int amount;

    @OneToOne(mappedBy = "invoice")
    private Installation installation;
}