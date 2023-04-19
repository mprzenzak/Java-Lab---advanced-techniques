package com.mprzenzak.lab06.services;

import com.mprzenzak.lab06.models.Installation;
import com.mprzenzak.lab06.repository.InstallationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstallationService {
    private final InstallationRepository installationRepository;

    public InstallationService(InstallationRepository installationRepository) {
        this.installationRepository = installationRepository;
    }

    public Installation save(Installation installation) {
        return installationRepository.save(installation);
    }

    public List<Installation> findAll() {
        return installationRepository.findAll();
    }

    public Installation findById(Long id) {
        return installationRepository.findById(id).orElseThrow(() -> new RuntimeException("Installation not found with id: " + id));
    }

    public void delete(Installation installation) {}
}
