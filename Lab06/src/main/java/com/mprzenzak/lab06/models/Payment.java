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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Invoice getPayment() {
        return payment;
    }

    public void setPayment(Invoice payment) {
        this.payment = payment;
    }
}