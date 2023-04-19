package com.mprzenzak.lab07.repository;

import com.mprzenzak.lab07.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Modifying
    @Query("update Client c set c.firstName = ?2 where c.id = ?1")
    public void updateClient(int i, String clientNumber);

    public Optional<Invoice> findById(Long id);
}