package com.mprzenzak.lab07.repository;

import com.mprzenzak.lab07.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Modifying
    @Query("update Payment p set p.amount = ?2 where p.id = ?1")
    public void updateClient(int i, String clientNumber);

    public Optional<Payment> findById(Long id);
}
