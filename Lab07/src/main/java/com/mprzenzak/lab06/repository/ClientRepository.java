package com.mprzenzak.lab06.repository;

import com.mprzenzak.lab06.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByClientNumber(String clientNumber);
    @Modifying
    @Query("update Client c set c.firstName = ?2 where c.id = ?1")
    public void updateClient(int i, String clientNumber);

    public Optional<Client> findById(Long id);
}
