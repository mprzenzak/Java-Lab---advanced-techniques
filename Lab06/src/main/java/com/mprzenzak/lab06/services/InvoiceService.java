package com.mprzenzak.lab06.services;

import com.mprzenzak.lab06.models.*;
import com.mprzenzak.lab06.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InstallationRepository installationRepository;
    private final PaymentRepository paymentRepository;
    private final PriceListRepository priceListRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, InstallationRepository installationRepository, PaymentRepository paymentRepository, PriceListRepository priceListRepository) {
        this.invoiceRepository = invoiceRepository;
        this.installationRepository = installationRepository;
        this.paymentRepository = paymentRepository;
        this.priceListRepository = priceListRepository;
    }

    @Autowired
    private ClientRepository clientRepository;

    @Scheduled(cron = "0 */1 * * * ?")
    public void generateInvoices() {
        List<Client> clients = clientRepository.findAll();
        List<PriceList> priceList = priceListRepository.findAll();

        for (Client client : clients) {
            Set<Installation> installations = client.getInstallations();

            for (Installation installation : installations) {
                Optional<PriceList> optionalPriceList = priceList.stream()
                        .filter(priceListItem -> priceListItem.getServiceType().equals(installation.getServiceType()))
                        .findFirst();

                PriceList priceListItem = optionalPriceList.get();
                generateAndSaveInvoice(installation, priceListItem);
            }
        }
    }

    @Async
    @Transactional
    public void generateAndSaveInvoice(Installation installation, PriceList priceListItem) {
        Invoice invoice = new Invoice();
        invoice.setInstallation(installation);
        invoice.setAmount(priceListItem.getAmount());
        invoice.setPaymentDate(LocalDateTime.now());

        invoiceRepository.save(invoice);
    }

    @Scheduled(cron = "0 0 0 15 * ?")
    public void sendPaymentReminders() {

    }
}
