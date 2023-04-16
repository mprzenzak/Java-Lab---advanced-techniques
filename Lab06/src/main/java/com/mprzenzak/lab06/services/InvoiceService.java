package com.mprzenzak.lab06.services;

import com.mprzenzak.lab06.repository.InstallationRepository;
import com.mprzenzak.lab06.repository.InvoiceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InstallationRepository installationRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, InstallationRepository installationRepository) {
        this.invoiceRepository = invoiceRepository;
        this.installationRepository = installationRepository;
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateInvoices() {

    }

    @Scheduled(cron = "0 0 0 15 * ?")
    public void sendPaymentReminders() {

    }
}
