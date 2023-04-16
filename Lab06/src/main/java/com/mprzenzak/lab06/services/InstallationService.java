package com.mprzenzak.lab06.services;

import com.mprzenzak.lab06.models.Installation;
import com.mprzenzak.lab06.repository.InstallationRepository;
import org.springframework.stereotype.Service;

@Service
public class InstallationService {
    private final InstallationRepository installationRepository;

    public InstallationService(InstallationRepository installationRepository) {
        this.installationRepository = installationRepository;
    }

    public Installation save(Installation installation) {
        return installationRepository.save(installation);
    }
}
