package com.mprzenzak.lab06.repository;

import com.mprzenzak.lab06.models.Installation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstallationRepository extends JpaRepository<Installation, Long> {
    public Optional<Installation> findById(Long id);
}
