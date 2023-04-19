package com.mprzenzak.lab07.controllers;

import com.mprzenzak.lab07.models.Invoice;
import com.mprzenzak.lab07.models.InvoiceWrapper;
import com.mprzenzak.lab07.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/invoices")
    public String showInvoices(Model model) {
        List<Invoice> invoices = invoiceRepository.findAll();
        model.addAttribute("invoices", invoices);
        return "invoices";
    }

    @PostMapping("/invoices/save")
    public String saveInvoices(@ModelAttribute("invoicesWrapper") InvoiceWrapper updatedInvoicesWrapper) {
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
        return "redirect:/invoices";
    }

    @ModelAttribute("invoicesWrapper")
    public InvoiceWrapper invoicesWrapper() {
        InvoiceWrapper invoiceWrapper = new InvoiceWrapper();
        invoiceWrapper.setInvoices(invoiceRepository.findAll());
        return invoiceWrapper;
    }

    @PostMapping("/invoices/register-payment")
    @Transactional
    public String registerPayment(@RequestParam("invoiceId") Long invoiceId, @RequestParam("paymentAmount") double paymentAmount) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);

        if (invoiceOptional.isPresent()) {
            Invoice invoice = invoiceOptional.get();
            invoice.setAmount(invoice.getAmount() - paymentAmount);
            invoice.setPaymentDate(LocalDateTime.now());
            invoiceRepository.save(invoice);
        }

        return "redirect:/invoices";
    }
}
