package com.mprzenzak.lab06.respository;

import com.mprzenzak.lab06.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Invoice, Long> {
}
