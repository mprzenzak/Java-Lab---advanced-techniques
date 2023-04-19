package com.mprzenzak.lab06.services;

import com.mprzenzak.lab06.models.*;
import com.mprzenzak.lab06.repository.*;
import com.opencsv.CSVWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final PriceListRepository priceListRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, PaymentRepository paymentRepository, PriceListRepository priceListRepository) {
        this.invoiceRepository = invoiceRepository;
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
        invoice.setPaymentDate(LocalDateTime.now().plusMinutes(2));
        invoiceRepository.save(invoice);

        Payment payment = new Payment();
        payment.setAmount(0);
        payment.setInvoice(invoice);
        paymentRepository.save(payment);

        writeInvoiceToCsv(invoice);
    }

    private void writeInvoiceToCsv(Invoice invoice) {
        String fileName = "new_invoices.csv";
        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
                try (FileWriter fileWriter = new FileWriter(fileName, true);
                     CSVWriter csvWriter = new CSVWriter(fileWriter)) {
                    String[] header = {"Numer faktury", "Imię", "Nazwisko", "Adres", "Typ usługi", "Kwota", "Termin płatności"};
                    csvWriter.writeNext(header);
                }
            }

            try (FileWriter fileWriter = new FileWriter(fileName, true);
                 CSVWriter csvWriter = new CSVWriter(fileWriter)) {
                String[] invoiceData = {
                        String.valueOf(invoice.getId()),
                        invoice.getInstallation().getClient().getFirstName(),
                        invoice.getInstallation().getClient().getLastName(),
                        invoice.getInstallation().getAddress(),
                        invoice.getInstallation().getServiceType().getLabel(),
                        String.valueOf(invoice.getAmount()),
                        invoice.getPaymentDate().toString()
                };
                csvWriter.writeNext(invoiceData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void sendPaymentReminders() {
        List<Invoice> invoices = invoiceRepository.findAll();
        List<String[]> latePayments = new ArrayList<>();

        for (Invoice invoice : invoices) {
            if (invoice.getPaymentDate().isBefore(LocalDateTime.now())) {
                String[] latePayment = {
                        invoice.getInstallation().getClient().getFirstName(),
                        invoice.getInstallation().getClient().getLastName(),
                        invoice.getInstallation().getAddress(),
                        String.valueOf(invoice.getInstallation().getServiceType().getLabel())
                };
                latePayments.add(latePayment);
            }
        }

        if (!latePayments.isEmpty()) {
            try {
                String fileName = "late_payments.csv";
                FileWriter fileWriter = new FileWriter(fileName);

                try (CSVWriter csvWriter = new CSVWriter(fileWriter)) {
                    String[] header = {"Imię", "Nazwisko", "Adres", "Typ usługi"};
                    csvWriter.writeNext(header);
                    csvWriter.writeAll(latePayments);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
