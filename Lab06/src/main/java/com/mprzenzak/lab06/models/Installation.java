package com.mprzenzak.lab06.models;

import jakarta.persistence.*;

@Entity
public class Installation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}