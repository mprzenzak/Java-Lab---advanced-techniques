package com.mprzenzak.lab06.restControllers;

import com.mprzenzak.lab06.models.Invoice;
import com.mprzenzak.lab06.models.InvoiceWrapper;
import com.mprzenzak.lab06.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceRestController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping
    public ResponseEntity<List<Invoice>> showInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveInvoices(@RequestBody InvoiceWrapper updatedInvoicesWrapper) {
        List<Invoice> updatedInvoices = updatedInvoicesWrapper.getInvoices();
        for (Invoice updatedInvoice : updatedInvoices) {
            Optional<Invoice> invoiceOptional = invoiceRepository.findById(updatedInvoice.getId());
            if (invoiceOptional.isPresent()) {
                Invoice invoice = invoiceOptional.get();
                invoice.setAmount(updatedInvoice.getAmount());
                invoice.setPaymentDate(updatedInvoice.getPaymentDate());
                invoice.setInstallation(updatedInvoice.getInstallation());
                invoiceRepository.save(invoice);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register-payment")
    public ResponseEntity<Void> registerPayment(@RequestParam("invoiceId") Long invoiceId, @RequestParam("paymentAmount") double paymentAmount) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);

        if (invoiceOptional.isPresent()) {
            Invoice invoice = invoiceOptional.get();
            invoice.setAmount(invoice.getAmount() - paymentAmount);
            invoice.setPaymentDate(LocalDateTime.now());
            invoiceRepository.save(invoice);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
