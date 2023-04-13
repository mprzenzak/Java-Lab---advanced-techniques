package com.mprzenzak.lab06.services;

import com.mprzenzak.lab06.respository.InstallationRepository;
import com.mprzenzak.lab06.respository.InvoiceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InstallationRepository installationRepository;

    // Inne zależności, np. PaymentService

    public InvoiceService(InvoiceRepository invoiceRepository, InstallationRepository installationRepository) {
        this.invoiceRepository = invoiceRepository;
        this.installationRepository = installationRepository;
    }

    @Scheduled(cron = "0 0 0 1 * ?") // Wykonaj co miesiąc, pierwszego dnia miesiąca
    public void generateInvoices() {
        // Logika generowania faktur dla każdej instalacji
    }

    @Scheduled(cron = "0 0 0 15 * ?") // Wykonaj co miesiąc, piętnastego dnia miesiąca
    public void sendPaymentReminders() {
        // Logika wysyłania monitów o płatnościach
    }
}
