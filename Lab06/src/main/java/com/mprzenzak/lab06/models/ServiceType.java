package com.mprzenzak.lab06.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}