package com.mprzenzak.lab07.repository;

import com.mprzenzak.lab07.models.Client;
import com.mprzenzak.lab07.models.Installation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstallationRepository extends JpaRepository<Installation, Long> {
    public Optional<Installation> findById(Long id);

    List<Installation> findByClient(Client client);
}
